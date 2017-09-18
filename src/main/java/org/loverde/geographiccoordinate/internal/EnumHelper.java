/*
 * GeographicCoordinate
 * https://github.com/kloverde/GeographicCoordinate
 *
 * Copyright (c) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * Donations:  https://paypal.me/KurtisLoVerde/5
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     2. Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *     3. Neither the name of the copyright holder nor the names of its
 *        contributors may be used to endorse or promote products derived from
 *        this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.loverde.geographiccoordinate.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;


public class EnumHelper {

   /**
    * Uses reflection to populate a map of enum members using a configurable getter from the enum
    *
    * @param enumClass The enumeration
    * @param keySourceGetterName The name of a public getter within the enumeration, which provides the keys for the map
    *
    * @return A map containing all members of the enumeration
    */
   public static final <E> Map<String, E> populateEnumMap_stringKey( final Class<E> enumClass, final String keySourceGetterName ) {
      final Map<String, E> map;
      final Method methods[];

      Method method = null;

      if( enumClass == null ) throw new IllegalArgumentException( "enumClass is null" );
      if( (keySourceGetterName + "").trim().isEmpty() ) throw new IllegalArgumentException( "keySourceGetterName is empty" );

      map = new LinkedHashMap<>();
      methods = enumClass.getMethods();

      if( methods.length > 0 ) {
         for( Method m : methods ) {
            if( m.getName().equals(keySourceGetterName) && m.getReturnType() == String.class ) {
               method = m;
            }
         }

         if( method == null ) {
            throw new IllegalArgumentException( "Could not find a matching String method named " + keySourceGetterName );
         }

         try {
            for( final E enumObj : enumClass.getEnumConstants() ) {
               map.put( (String) method.invoke(enumObj, (Object[]) null), enumObj );
            }
         } catch( IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            throw new RuntimeException( e );
         }
      }

      return map;
   }
}
