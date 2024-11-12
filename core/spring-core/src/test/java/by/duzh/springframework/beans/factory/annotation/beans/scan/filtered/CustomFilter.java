package by.duzh.springframework.beans.factory.annotation.beans.scan.filtered;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class CustomFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata metadata = metadataReader.getClassMetadata();
        String className = metadata.getClassName();
        return className.endsWith("CustomFiltered");
    }
}
