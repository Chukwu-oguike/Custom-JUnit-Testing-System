public class AssertBool {

    private boolean helper;

    public AssertBool(boolean b){
        helper = b;
    }

    public AssertBool isEqualTo(boolean b2){
        if(helper == b2){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertBool isTrue(){
        if(helper == true){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }
    }

    public AssertBool isFalse(){
        if(helper == false){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }
    }

}
