public class AssertInt {

    private int helper;

    public AssertInt(int i){
        helper = i;
    }

    public AssertInt isEqualTo(int i2){
        if(helper == i2){
            return this;
        }else{
            throw new UnsupportedOperationException();
        }

    }

    public AssertInt isLessThan(int i2){
        if(helper >= i2){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

    public AssertInt isGreaterThan(int i2){
        if(helper <= i2){
            throw new UnsupportedOperationException();
        }else{
            return this;
        }

    }

}
