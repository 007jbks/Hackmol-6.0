package com.example.pets.petlisting

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PetListingScreen(
    viewModel: PetListingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    getUserToken: () -> String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    // Create a temporary file for the camera photo
    val tempPhotoFile = remember { createImageFile(context) }
    val photoUri = remember {
        tempPhotoFile?.let {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                it
            )
        }
    }

    // Camera launcher - declared first
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            viewModel.updatePhotoUri(photoUri)
            tempPhotoFile?.let { file ->
                viewModel.updatePhotoPath(file.absolutePath)
            }
        }
    }

    // Camera permission state
    val (hasCameraPermission, setCameraPermission) = remember { mutableStateOf(
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    )}

    // Permission launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        setCameraPermission(isGranted)
        if (isGranted) {
            // Launch camera if permission is granted
            photoUri?.let { cameraLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            viewModel.updatePhotoUri(selectedUri)
            // Get file path from URI and update in ViewModel
            getFilePathFromUri(context, selectedUri)?.let { path ->
                viewModel.updatePhotoPath(path)
            }
        }
    }

    // Handle success or error states
    LaunchedEffect(uiState.success, uiState.error) {
        uiState.success?.let {
            Toast.makeText(context, "Pet listed successfully!", Toast.LENGTH_SHORT).show()
            onNavigateBack()
        }

        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "List Your Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Pet Name Field
            OutlinedTextField(
                value = uiState.petName,
                onValueChange = { viewModel.updatePetName(it) },
                label = { Text("Pet Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Pet Species Field
            OutlinedTextField(
                value = uiState.petSpecies,
                onValueChange = { viewModel.updatePetSpecies(it) },
                label = { Text("Pet Species") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true
            )

            // Photo Section
            Text(
                text = "Pet Photo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Photo Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.photoUri != null) {
                    AsyncImage(
                        model = uiState.photoUri,
                        contentDescription = "Pet Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "No Photo",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            // Photo Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        if (hasCameraPermission) {
                            photoUri?.let { cameraLauncher.launch(it) }
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Camera",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Take Photo")
                }

                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Gallery",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Gallery")
                }
            }

            // Submit Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.createPetListing(getUserToken())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp),
                enabled = !uiState.isLoading
            ) {
                Text("Submit Pet Listing")
            }
        }

        // Loading Indicator
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

// Utility functions

private fun createImageFile(context: Context): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storageDir = context.getExternalFilesDir(null)

    return runCatching {
        File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }.getOrNull()
}

private fun getFilePathFromUri(context: Context, uri: Uri): String? {
    return if (uri.scheme == "file") {
        uri.path
    } else {
        val tempFile = createImageFile(context)
        tempFile?.let {
            context.contentResolver.openInputStream(uri)?.use { input ->
                it.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            it.absolutePath
        }
    }
}