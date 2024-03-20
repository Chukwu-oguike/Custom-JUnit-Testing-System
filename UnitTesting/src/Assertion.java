//import static org.assertj.core.api.Assertions.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Assertion {

    public Assertion(){

    }


    /* You'll need to change the return type of the assertThat methods */
    static AssertObj assertThat(Object o) {

        AssertObj ob = new AssertObj(o);

        return ob;

        //throw new UnsupportedOperationException();
    }
    static AssertStr assertThat(String s) {

        AssertStr strr = new AssertStr(s);

        return strr;

        //throw new UnsupportedOperationException();
    }
    static AssertBool assertThat(boolean b) {


        AssertBool bll = new AssertBool(b);

        return bll;
        // throw new UnsupportedOperationException();
    }
    static AssertInt assertThat(int i) {

        AssertInt intt = new AssertInt(i);

        return intt;
        //throw new UnsupportedOperationException();
    }





}