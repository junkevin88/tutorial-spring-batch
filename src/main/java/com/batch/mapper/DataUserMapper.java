package com.batch.mapper;

import com.batch.entity.DataUser;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DataUserMapper implements FieldSetMapper {
    @Override
    public DataUser mapFieldSet(FieldSet fieldSet) throws BindException {
        return new DataUser(
                fieldSet.readString("Name"),
                fieldSet.readString("City"),
                fieldSet.readInt("Code"));
    }
}