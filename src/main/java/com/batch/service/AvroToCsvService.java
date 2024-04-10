package com.batch.service;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class AvroToCsvService implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Implement Avro to CSV conversion logic here
        convertAvroToCsv("users.avro", "users.csv");  // Call your existing conversion method
        return RepeatStatus.FINISHED;
    }

    public static void convertAvroToCsv(String avroFilePath, String csvFilePath) throws IOException {
        // Load Avro schema from the resources directory
        InputStream schemaInputStream = AvroToCsvService.class.getResourceAsStream("/users.avsc");
        Schema schema = new Schema.Parser().parse(schemaInputStream);
        schemaInputStream.close();

        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);


        try (FileReader<GenericRecord> dataFileReader = DataFileReader.openReader(new File(avroFilePath), datumReader);
             BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

            StringBuilder header = new StringBuilder();
            for (Schema.Field field : schema.getFields()) {
                header.append(field.name()).append(",");
            }
            writer.write(header.substring(0, header.length() - 1) + "\n");

            for (GenericRecord record : dataFileReader) {
                StringBuilder line = new StringBuilder();
                for (Schema.Field field : schema.getFields()) {
                    line.append(record.get(field.name())).append(",");
                }
                writer.write(line.substring(0, line.length() - 1) + "\n");
            }
        }
    }
}

