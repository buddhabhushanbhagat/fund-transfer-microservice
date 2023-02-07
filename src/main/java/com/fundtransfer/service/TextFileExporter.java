package com.fundtransfer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextFileExporter implements FileExporter {
	
	private static final String EXPORT_DIRECTORY = "D:\\TransactionReports";
	private Logger logger = LoggerFactory.getLogger(TextFileExporter.class);
	
	@Override
	public Path export(String fileContent, String fileName) throws IOException {
		Path filePath = Paths.get(EXPORT_DIRECTORY, fileName);
			Path exportedFilePath = Files.write(filePath, fileContent.getBytes(), StandardOpenOption.CREATE);
			return exportedFilePath;	
	}
}
