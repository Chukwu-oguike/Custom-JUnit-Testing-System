public class AssertStr {

    private String helper;

    public AssertStr(String o){
        helper = o;
    }


    public AssertStr isNotNull(){
        if(helper == null){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

    public AssertStr isNull(){
        if(helper == null){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertStr isEqualTo(Object o2){
        if(o2.equals(helper)){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertStr isNotEqualTo(Object o2){
        if(o2 == null){

            if(helper== null){
                throw new UnsupportedOperationException();
            }

        }

        if(!helper.equals(o2)){
            return this;
        }

        else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertStr isEmpty(){
        if(helper != ""){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

    public AssertStr contains(String s2){
        if(!helper.contains(s2)){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

    public AssertStr startsWith(String s2){
        if(!helper.startsWith(s2)){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }


}
