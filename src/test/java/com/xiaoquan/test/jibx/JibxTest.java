package com.xiaoquan.test.jibx;

import com.xiaoquan.gradle.plugin.model.FlightType;
import com.xiaoquan.gradle.plugin.model.Flights;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class JibxTest {

    String uft8 = "UTF-8";

    @Test
    public void test1() throws Exception {
        Flights ping = new Flights();
        FlightType type = new FlightType();
        type.setNumber("9");
        ping.addFlight(type);

        IBindingFactory iBindingFactory = BindingDirectory.getFactory("flights", Flights.class);

        IMarshallingContext marshallingContext = iBindingFactory.createMarshallingContext();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        marshallingContext.marshalDocument(ping, uft8, null, out);


        String xml = out.toString(uft8);


        System.out.println(xml);
        System.out.println("h");



    }


    @Test
    public void test3()throws Exception {
//        Compile compiler = new Compile();
//
//
//        compiler.setLoad(false);
////        compiler.setSkipValidate(!this.validate);
//        compiler.setVerbose(false);
////        compiler.setVerify(this.verify);
//        compiler.compile(classpaths, bindings);
    }



}
