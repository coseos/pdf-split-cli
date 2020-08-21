package com.coseos.pdf.split;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PdfSplitCliTest {
	
	private PdfSplitCli cut;

	@BeforeEach
	public void setup() {		
		cut = new PdfSplitCli();
	}

	@Test
	public void givenPdfDocument_whenRun_thenSplitDocument(@TempDir final Path tempdir) throws IOException {
		// Arrange
		Path tempfile = tempdir.resolve("test.pdf");
		final byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/test.pdf"));
		Files.write(tempfile, bytes, StandardOpenOption.CREATE);
		
		// Act
		int result = cut.run(new String[] { tempfile.toFile().getAbsolutePath()});
		
		// Assert
		Stream<Path> files = Files.list(tempdir);

		assertThat(result, is(0));
		assertThat(files,is(notNullValue()));
		assertThat(files.count(), is(4L));
	}
}
