package com.coseos.pdf.split;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfSplitCli {

	private static final String FILE_SUFFIX_PDF = ".pdf";

	private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

	public int run(String[] args) {
		
		File file = new File(args[0]);

		if (file.exists() && file.getName().toLowerCase().endsWith(FILE_SUFFIX_PDF)) {
			String fileNameWithoutSuffix = file.getAbsolutePath().substring(0,
					file.getAbsolutePath().length() - FILE_SUFFIX_PDF.length());

			try {
				PDDocument pdfDocument = PDDocument.load(file);
				Splitter splitter = new Splitter();

				List<PDDocument> pages = splitter.split(pdfDocument);

				int i = 0;
				for (PDDocument pdfPage : pages) {
					pdfPage.save(String.format("%s-%d.pdf", fileNameWithoutSuffix, i++));
				}
				return 0;
			} catch (IOException ioException) {
                logger.severe( () -> String.format("IO exception %s", ioException.getMessage()));
                return -1;
			}
		}
		else {
			logger.warning( () -> String.format("File not found: %s", file.getAbsolutePath()));
			return 1;
		}
	}
}
