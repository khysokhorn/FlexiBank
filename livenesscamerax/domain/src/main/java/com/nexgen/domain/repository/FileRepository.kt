package com.nexgen.domain.repository

import java.io.File

interface FileRepository {
    fun getPhotoFile(): File
    fun deleteStorageFiles(): Boolean
    fun getPathOfAllPhotos(): List<String>
}
