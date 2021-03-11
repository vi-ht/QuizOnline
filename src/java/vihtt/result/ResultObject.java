/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thanh Vi
 */
public class ResultObject implements Serializable{
         Map<Integer, Boolean> result;

         public Map<Integer, Boolean> getResutls() {
                  return result;
         }
         
         public int resultSize() {
                  return this.result.size();
         }
         public boolean resultValue(int key) {
                  return this.result.get(key);
         }
         
         public void addResult(int quantity, boolean result) {
                  if (this.result == null) {
                           this.result = new HashMap<>();
                  }
                  for (int i = 0; i < quantity; i++) {
                           this.result.put(i, result);
                  }
                  
         }
         
         public void updateResult(int pos, boolean result) {
                  if (this.result.containsKey(pos)) {
                           this.result.replace(pos, result);
                  }
                  
         }

}
