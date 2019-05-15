package com.alex.phone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  21:40 2019/5/15
 * @modified By:
 */
@Component
public class PhoneInfoService implements InitializingBean {

    private static final String PHONE_DAT_FILE_PATH = "phone.dat";

    private static byte[] dataByteArray;

    @Override
    public void afterPropertiesSet() throws Exception {
        ByteArrayOutputStream byteData = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int readBytesLength;
        try {
            // 读取配置文件中的手机号数据信息
            InputStream phoneInfoStream = PhoneInfoService.class.getClassLoader().getResourceAsStream(PHONE_DAT_FILE_PATH);
            while ((readBytesLength = phoneInfoStream.read(buffer)) != -1) {
                byteData.write(buffer, 0, readBytesLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        dataByteArray = byteData.toByteArray();

    }
}
