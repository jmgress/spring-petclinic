/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service for handling file storage operations.
 */
@Service
public class FileStorageService {

	@Value("${petclinic.images.upload-dir}")
	private String uploadDir;

	/**
	 * Stores a file and returns the file name.
	 * @param file The file to store
	 * @return The name of the stored file
	 * @throws IOException If an error occurs during file storage
	 */
	public String storeFile(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return null;
		}

		// Generate a unique file name to prevent conflicts
		String originalFileName = file.getOriginalFilename();
		String fileExtension = "";
		if (originalFileName != null && originalFileName.contains(".")) {
			fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		}
		String fileName = UUID.randomUUID() + fileExtension;

		// Create the directory if it doesn't exist
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Copy the file to the upload directory
		Path destinationPath = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

		// Return the relative path for storing in the database
		return "/resources/images/pets/" + fileName;
	}

	/**
	 * Validates if the file is a supported image type.
	 * @param file The file to validate
	 * @return true if the file is a supported image, false otherwise
	 */
	public boolean isValidImageFile(MultipartFile file) {
		if (file.isEmpty()) {
			return true; // Empty file is valid (optional)
		}

		String contentType = file.getContentType();
		return contentType != null && (
			contentType.equals("image/jpeg") ||
			contentType.equals("image/png") ||
			contentType.equals("image/jpg")
		);
	}
}