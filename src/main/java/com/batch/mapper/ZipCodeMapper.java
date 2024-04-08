package com.batch.mapper;

import com.batch.entity.ZipCode;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ZipCodeMapper implements FieldSetMapper<ZipCode> {

    @Override
    public ZipCode mapFieldSet(FieldSet fieldSet) throws BindException {
        return new ZipCode(
                fieldSet.readString("Zip_Code"),
                fieldSet.readString("Official_USPS_city_name"),
                fieldSet.readString("Official_USPS_State_Code"));
    }
}