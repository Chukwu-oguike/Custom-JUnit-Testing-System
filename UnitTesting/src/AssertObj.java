public class AssertObj {

    private Object helper;

    public AssertObj(Object o){
        helper = o;
    }


    public AssertObj isNotNull(){
        if(helper == null){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

    public AssertObj isNull(){
        if(helper == null){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertObj isEqualTo(Object o2){
        if(o2.equals(helper)){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertObj isNotEqualTo(Object o2){
        if(o2 ==null){

            if(helper==null){
                throw new UnsupportedOperationException();
            }

        }

        if(!helper.equals(o2)){
            return this;
        } else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertObj isInstanceOf(Class c){

        // Assertion a = new Assertion();
        if (c.isInstance(helper)){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }
    }
}
