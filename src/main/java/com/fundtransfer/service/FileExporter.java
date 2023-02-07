package com.fundtransfer.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;

public interface FileExporter {
	public Path export(String fileContent, String fileName) throws IOException;
}
