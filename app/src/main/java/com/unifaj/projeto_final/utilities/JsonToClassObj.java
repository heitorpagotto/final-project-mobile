//package com.unifaj.projeto_final.utilities;
//
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//
//public class JsonToClassObj {
//    /**
//     * Método usado para conversão genérica de JSON string em uma instância de objeto tipado por classe.
//     * Para utilizar este método é preciso que a clase não possua um construtor com parâmetro obrigatórios e é necessário ter métodos set para todas as propriedades
//     *
//     * @param classToConvert Classe em que se converterá a String JSON
//     * @param json           String JSON
//     * @param <T>
//     * @return
//     */
//    public <T> T convertToObj(Class<T> classToConvert, String json) {
//        try {
//            Field[] fields = classToConvert.getDeclaredFields();
//            JSONObject jsonObj = new JSONObject(json);
//
//            T genericInstance = classToConvert.getDeclaredConstructor().newInstance();
//
//            for (Field field : fields) {
//                Object value = jsonObj.get(field.getName());
//                String firstCharacter = field.getName().substring(0, 1).toUpperCase();
//                String methodName = "set" + firstCharacter + field.getName().substring(1);
//
//                Method[] methods = classToConvert.getDeclaredMethods();
//                for (Method method : methods) {
//                    if (method.getName().equals(methodName)) {
//                        if (value instanceof JSONArray) {
//                            ArrayList<Object> listdata = new ArrayList<>();
//                            JSONArray jArray = (JSONArray) value;
//                            if (jArray != null) {
//                                for (int i = 0; i < jArray.length(); i++) {
//                                    listdata.add(jArray.get(i));
//                                }
//                            }
//                            method.invoke(genericInstance, listdata);
//                        } else if (value instanceof JSONObject) {
//                            Object obj = convertToObj(Object.class, value.toString());
//                            method.invoke(genericInstance, obj);
//                        } else {
//                            method.invoke(genericInstance, value);
//                        }
//                    }
//                }
//            }
//
//            return genericInstance;
//        } catch (JSONException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public <T> T convertToObjUnknown(Class<?> classToConvert, String json) {
//        try {
//            Field[] fields = classToConvert.getDeclaredFields();
//            JSONObject jsonObj = new JSONObject(json);
//
//            T genericInstance = (T) classToConvert.getDeclaredConstructor().newInstance();
//
//            for (Field field : fields) {
//                Object value = jsonObj.get(field.getName());
//                String firstCharacter = field.getName().substring(0, 1).toUpperCase();
//                String methodName = "set" + firstCharacter + field.getName().substring(1);
//
//                Method[] methods = classToConvert.getDeclaredMethods();
//                for (Method method : methods) {
//                    if (method.getName().equals(methodName)) {
//                        if (value instanceof JSONArray) {
//                            ArrayList<Object> listdata = new ArrayList<>();
//                            JSONArray jArray = (JSONArray) value;
//                            if (jArray != null) {
//                                for (int i = 0; i < jArray.length(); i++) {
//                                    listdata.add(jArray.get(i));
//                                }
//                            }
//                            method.invoke(genericInstance, listdata);
//                        } else if (value instanceof JSONObject) {
//                            Object obj = convertToObj(Object.class, value.toString());
//                            method.invoke(genericInstance, obj);
//                        } else {
//                            method.invoke(genericInstance, value);
//                        }
//                    }
//                }
//            }
//
//            return genericInstance;
//        } catch (JSONException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public <T> T convertToObjBaseClass(Class<T> classToConvert, Class<?> baseClass, String json) {
//        try {
//            Field[] fields = classToConvert.getDeclaredFields();
//            JSONObject jsonObj = new JSONObject(json);
//
//            T genericInstance = classToConvert.getDeclaredConstructor().newInstance();
//
//            for (Field field : fields) {
//                Object value = jsonObj.get(field.getName());
//                String firstCharacter = field.getName().substring(0, 1).toUpperCase();
//                String methodName = "set" + firstCharacter + field.getName().substring(1);
//
//                Method[] methods = classToConvert.getDeclaredMethods();
//                for (Method method : methods) {
//                    if (method.getName().equals(methodName)) {
//                        if (value instanceof JSONArray) {
//                            ArrayList<Class<?>> listdata = new ArrayList<>();
//                            JSONArray jArray = (JSONArray) value;
//                            if (jArray != null) {
//                                for (int i = 0; i < jArray.length(); i++) {
//                                    listdata.add(convertToObjUnknown(baseClass.getClass(), jArray.get(i).toString()));
//                                }
//                            }
//                            method.invoke(genericInstance, listdata);
//                        } else if (value instanceof JSONObject) {
//                            Class<?> obj = convertToObjUnknown(baseClass.getClass(), value.toString());
//                            method.invoke(genericInstance, obj);
//                        } else {
//                            method.invoke(genericInstance, value);
//                        }
//                    }
//                }
//            }
//
//            return genericInstance;
//        } catch (JSONException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            Log.e("Error", e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
