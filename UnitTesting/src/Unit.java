import java.util.*;
import java.lang.reflect.*;
import java.lang.Math;
//import java.lang.reflect.Field;
//import java.lang.reflect.Constructor;
import java.lang.annotation.Annotation;


public class Unit {

    public Unit(){

    }

    public static HashMap<String, Throwable> testClass(String name) throws Exception  {
        HashMap<String, Throwable> map = new HashMap<String, Throwable>();
        Class<?> c = Class.forName(name);
        Method[] methods = c.getMethods();

        String[] m = new String[methods.length];

        // sort methods
        for(int i=0; i < methods.length; i++){
           m[i] = methods[i].getName();
        }

        Arrays.sort(m);
        Method temp;

        for(int i=0; i < methods.length; i++){
            for (int j=0; j < methods.length; j++){
                if(methods[i].getName().equals(m[j])){
                    temp = methods[i];
                    methods[i] = methods[j];
                    methods[j] = temp;
                }
            }
        }

        //
        for (Method method : methods) {
            BeforeClass annos = method.getAnnotation(BeforeClass.class);
            if (annos != null && Modifier.isStatic(method.getModifiers())) {
                method.invoke(null);
            }
        }



        for (Method method : methods) {
            Test annosT = method.getAnnotation(Test.class);
            if (annosT != null) {

                for (Method methodB : methods) {
                    Before annosB = methodB.getAnnotation(Before.class);
                    if (annosB != null) {
                        methodB.invoke(c);

                    }

                }

                try {
                    method.invoke(c);
                    map.put(method.getName(), null);
                } catch (Exception e) {
                    if(e instanceof InvocationTargetException){
                        map.put(method.getName(), e.getCause());
                    } else{
                        map.put(method.getName(), e);
                    }
                }

                for (Method methodA : methods) {
                    After annosA = methodA.getAnnotation(After.class);
                    if (annosA != null) {
                        methodA.invoke(c);
                    }

                }
            }


        }

        for (Method method : methods) {
            AfterClass annos = method.getAnnotation(AfterClass.class);
            if (annos != null) {
                method.invoke(c);
            }
        }

        return map;
        //throw new UnsupportedOperationException();
    }


    public static HashMap<String, Object[]> quickCheckClass(String name) throws Exception {

        HashMap<String, Object[]> map2 = new HashMap<String, Object[]>();
        Class<?> c1 = Class.forName(name);
        Method[] methods = c1.getMethods();

        String[] m = new String[methods.length];

        // sort methods
        for(int i=0; i < methods.length; i++){
            m[i] = methods[i].getName();
        }

        Arrays.sort(m);
        Method temp;

        for(int i=0; i < methods.length; i++){
            for (int j=0; j < methods.length; j++){
                if(methods[i].getName().equals(m[j])){
                    temp = methods[i];
                    methods[i] = methods[j];
                    methods[j] = temp;
                }
            }
        }

        for (Method method : methods) {
            Property annos = method.getAnnotation(Property.class);
            if (annos != null) {

                Annotation[][] annotations = method.getParameterAnnotations();
                if(annotations[0][0] instanceof IntRange){
                    Integer intMax = ((IntRange) annotations[0][0]).max();
                    Integer intMin = ((IntRange) annotations[0][0]).min();

                    Integer intervall = (int) Math.ceil((intMax - intMin) / 50);
                    Integer current = intMin;

                    ArrayList<Integer> fails = new ArrayList<Integer>();

                    while(current < intMax){

                        try {
                            Object bll = method.invoke(c1, current);
                            if (bll.equals(false))
                                fails.add(current);
                        } catch (Exception e) {

                            if(e instanceof InvocationTargetException){
                                if(e.getCause() instanceof Throwable)
                                    fails.add(current);

                            } else if(e instanceof Throwable){
                                fails.add(current);
                            }

                        }

                        current = current + intervall;

                    }

                    if(fails.size() == 0) {
                        map2.put(method.getName(), null);
                    } else{
                        Object[] objects = fails.toArray();
                        map2.put(method.getName(), objects);
                    }

                }else if(annotations[0][0] instanceof StringSet){
                    String[] s1 = ((StringSet) annotations[0][0]).strings();

                    ArrayList<String> fails = new ArrayList<String>();

                    for(int i=0; i<100; i++){

                        if(i == s1.length)
                            break;

                        try {
                            Object bll = method.invoke(c1, s1[i]);
                            if (bll.equals(false))
                                fails.add(s1[i]);
                        } catch (Exception e) {

                            if(e instanceof InvocationTargetException){
                                if(e.getCause() instanceof Throwable)
                                    fails.add(s1[i]);

                            } else if(e instanceof Throwable){
                                fails.add(s1[i]);
                            }

                        }


                    }

                    if(fails.size() == 0) {
                        map2.put(method.getName(), null);
                    } else{
                        Object[] objects = fails.toArray();
                        map2.put(method.getName(), objects);
                    }
                }else if(annotations[0][0] instanceof ListLength){

                    Integer listLenMax = ((ListLength) annotations[0][0]).max();
                    Integer listLenMin = ((ListLength) annotations[0][0]).min();

                    Parameter[] param = method.getParameters();
                    AnnotatedType type = param[0].getAnnotatedType();
                    Annotation[] annotes = type.getDeclaredAnnotations();
                    if(annotes[1] instanceof IntRange) {
                        Integer listIntMax = ((IntRange) annotes[1]).max();
                        Integer listIntMin = ((IntRange) annotes[1]).min();

                        Integer[] rangee = new Integer[listIntMax - listIntMin];
                        Integer current = (int) listIntMax;
                        for (int i = 0; i < rangee.length; i++) {
                            rangee[i] = current;
                            current++;
                        }

                        List<List<Integer>> vals = subsetLen(rangee, listLenMin, listLenMax);

                        List<List<Integer>> fails = new ArrayList<>();

                        for (int i = 0; i < 100; i++) {

                            if(i == vals.size())
                                break;

                            try {
                                Object bll = method.invoke(c1, vals.get(i));
                                if (bll.equals(false))
                                    fails.add(vals.get(i));
                            } catch (Exception e) {

                                if (e instanceof InvocationTargetException) {
                                    if (e.getCause() instanceof Throwable)
                                        fails.add(vals.get(i));

                                } else if (e instanceof Throwable) {
                                    fails.add(vals.get(i));
                                }

                            }
                        }

                        if (fails.size() == 0) {
                            map2.put(method.getName(), null);
                        } else {
                            Object[] objects = fails.toArray();
                            map2.put(method.getName(), objects);
                        }

                    }



                } else if(annotations[0][0] instanceof ForAll){
                    String funcName = ((ForAll) annotations[0][0]).name();
                    int intTimes = ((ForAll) annotations[0][0]).times();

                    Method meth = c1.getClass().getDeclaredMethod(funcName);
                    List<Object> fails = new ArrayList<>();
                    //Object[] obj = new Object[intTimes];

                    for(int i=0; i < 100; i++){

                        if(i == intTimes)
                            break;

                        try {
                            Object bll = method.invoke(c1, meth.invoke(c1));
                            if (bll.equals(false))
                                fails.add(meth.invoke(c1));
                        } catch (Exception e) {

                            if(e instanceof InvocationTargetException){
                                if(e.getCause() instanceof Throwable)
                                    fails.add(meth.invoke(c1));

                            } else if(e instanceof Throwable){
                                fails.add(meth.invoke(c1));
                            }

                        }


                    }

                    if(fails.size() == 0) {
                        map2.put(method.getName(), null);
                    } else{
                        Object[] objects = fails.toArray();
                        map2.put(method.getName(), objects);
                    }



                }
            }
        }


        return map2;
    }

    public static List<List<Integer>> subsetLen(Integer[] a, Integer min, Integer max){

        List<List<Integer>> b = subsets(a, 0);

        List<List<Integer>> c = new ArrayList<>();

        for(int i=0; i<b.size(); i++){

            if(min <= b.get(i).size() && b.get(i).size() <=max)
                c.add(b.get(i));
        }

        return c;

    }

    public static List<List<Integer>> subsets(Integer[] nums, Integer start) {
        List<List<Integer>> ret = new ArrayList<>();
        if (start == nums.length) {
            // Return empty list at the end
            ret.add(new ArrayList<>());
        } else {
            // Generate all subsequent lists
            List<List<Integer>> subsets = subsets(nums, start + 1);

            // They are all valid
            ret.addAll(subsets);

            // Also support the case where they are all prefixed with the
            // current value
            for (List<Integer> subset : subsets) {
                List<Integer> clone = new ArrayList<>(subset);
                clone.add(nums[start]);
                ret.add(clone);
            }
        }
        return ret;
    }

//    public static List<List<String>> subsetLenS(String[] a, Integer min, Integer max){
//
//        List<List<String>> b = subsetsS(a, 0);
//
//        List<List<String>> c = new ArrayList<>();
//
//        for(int i=0; i<b.size(); i++){
//
//            if(min <= b.get(i).size() && b.get(i).size() <=max)
//                c.add(b.get(i));
//        }
//
//        return c;
//
//    }

//    public static List<List<String>> subsetsS(String[] nums, int start) {
//        List<List<String>> ret = new ArrayList<>();
//        if (start == nums.length) {
//            // Return empty list at the end
//            ret.add(new ArrayList<>());
//        } else {
//            // Generate all subsequent lists
//            List<List<String>> subsets = subsetsS(nums, start + 1);
//
//            // They are all valid
//            ret.addAll(subsets);
//
//            // Also support the case where they are all prefixed with the
//            // current value
//            for (List<String> subset : subsets) {
//                List<String> clone = new ArrayList<>(subset);
//                clone.add(nums[start]);
//                ret.add(clone);
//            }
//        }
//        return ret;
//    }
}