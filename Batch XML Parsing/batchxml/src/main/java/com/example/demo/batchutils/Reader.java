package com.example.demo.batchutils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.demo.dto.CollegeDTO;
import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.StudentDTO;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class Reader {

	public SynchronizedItemStreamReader<StudentDTO> reader() 
	{
		StaxEventItemReader<StudentDTO> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new ClassPathResource("students.xml"));
        xmlFileReader.setFragmentRootElementName("student");

        Map<String, Class<?>> aliases = new HashMap<>();
        aliases.put("group", GroupDTO.class);
        aliases.put("college", CollegeDTO.class);
        aliases.put("student", StudentDTO.class);

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);

        String dateFormat = "yyyy-MM-dd";
        String timeFormat = "HHmmss";
        String[] acceptableFormats = {timeFormat};

        xStreamMarshaller.getXStream().autodetectAnnotations(true);
        xStreamMarshaller.getXStream().registerConverter(new DateConverter(dateFormat, acceptableFormats));


        xStreamMarshaller.getXStream().addPermission(NoTypePermission.NONE);
        xStreamMarshaller.getXStream().addPermission(NullPermission.NULL);
        xStreamMarshaller.getXStream().addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStreamMarshaller.getXStream().allowTypeHierarchy(Collection.class);
        xStreamMarshaller.getXStream().allowTypesByWildcard(new String[] {"com.example.demo.**"});

        xStreamMarshaller.getXStream().addImplicitCollection(GroupDTO.class, "list");          

        xmlFileReader.setUnmarshaller(xStreamMarshaller);      

        SynchronizedItemStreamReader<StudentDTO> synchronizedItemStreamReader = new SynchronizedItemStreamReader<>();
        synchronizedItemStreamReader.setDelegate(xmlFileReader);
        return synchronizedItemStreamReader;
	}

}
