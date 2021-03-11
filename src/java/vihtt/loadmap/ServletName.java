/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.loadmap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thanh Vi
 */
public class ServletName implements Serializable{
          Map<String,String> dto;
          
         public Map<String,String> getservletName(){
                  return dto;
         }

         public void addDTO(String key, String value){
                  if(this.dto==null){
                           this.dto = new HashMap<>();
                  }
                  this.dto.put(key, value);
         }
}
