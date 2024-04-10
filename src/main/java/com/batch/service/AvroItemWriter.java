package com.batch.service;

import com.batch.entity.DataUser;
import com.batch.repository.UserDataRepository;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Primary
public class AvroItemWriter implements ItemWriter<GenericRecord> {

    @Autowired
    public UserDataRepository userDataRepository;

    private Schema schema; // Load schema from a file

    @Override
    public void write(List<? extends GenericRecord> users) throws Exception {
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(new GenericDatumWriter<>(schema))) {
            dataFileWriter.create(schema, new File("users.avro"));
            List<DataUser> dataUsers = userDataRepository.findAll(); // Assuming getAllUsers() returns a list of User objects
            for (DataUser user : dataUsers) {
                GenericRecord genericRecord = new GenericData.Record(schema);
                genericRecord.put("name", user.getName());
                genericRecord.put("city", user.getCity());
                genericRecord.put("code", user.getCode());
                // ... Add other fields based on your schema

                dataFileWriter.append(genericRecord);
            }
        }
    }

    @PostConstruct // Load schema after bean creation
    public void init() throws IOException {
        InputStream schemaInputStream = AvroItemWriter.class.getResourceAsStream("/users.avsc");
        schema = new Schema.Parser().parse(schemaInputStream);
        schemaInputStream.close();
    }
}
