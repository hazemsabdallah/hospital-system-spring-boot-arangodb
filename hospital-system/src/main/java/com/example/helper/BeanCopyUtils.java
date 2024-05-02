package com.example.helper;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

// reference: https://gist.github.com/alankarapte/902bfe20c3e8e6a1ad82d5e4d7cc164c

// this class is used in the update operations to make it possible to update just a single prooperty w/o having
// to pass other fields too

public class BeanCopyUtils {

    public static void copyNonNullProperties(Object source, Object destination, String... ignoreProperties){

        Set<String> ignorePropertiesSet = getNullPropertyNames(source);
        ignorePropertiesSet.addAll(Arrays.asList(ignoreProperties));
        String[] ignorePropertiesArray = ignorePropertiesSet.toArray(new String[0]);
        BeanUtils.copyProperties(source, destination, ignorePropertiesArray);
    }

    public static Set<String> getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            // check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames;
    }
}