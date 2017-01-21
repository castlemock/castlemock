/*
 * Copyright 2017 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.core.basis.utility.compare;

/**
 * @author Karl Dahlgren
 * @since 1.8
 */
public class UrlComparer {

    protected static final String SLASH = "/";
    protected static final char START_BRACKET = '{';
    protected static final char END_BRACKET = '}';

    public static boolean compareUri(String uri1, String[] uriParts2){
        final String[] uriParts1 = uri1.split(SLASH);
        return compareUri(uriParts1, uriParts2);
    }

    public static boolean compareUri(String uri1, String uri2){
        final String[] uriParts1 = uri1.split(SLASH);
        final String[] uriParts2 = uri2.split(SLASH);
        return compareUri(uriParts1, uriParts2);
    }

    /**
     * The method provides the functionality to compare two sets of REST resource URI parts.
     * @param uriParts1 THe first set of resource URI parts
     * @param uriParts2 The second set of resource URI parts
     * @return True if the provided URIs are matching. False otherwise
     */
    public static boolean compareUri(final String[] uriParts1, final String[] uriParts2){
        if(uriParts1.length != uriParts2.length){
            return false;
        }

        for(int index = 0; index < uriParts1.length; index++){
            final String uriPart1 = uriParts1[index];
            final String uriPart2 = uriParts2[index];

            if(!comparePart(uriPart1, uriPart2)){
                return false;
            }

        }
        return true;
    }

    public static boolean comparePart(String uriPart1, String uriPart2){
        int startBracketIndex = uriPart1.indexOf(START_BRACKET);
        int endBracketIndex = uriPart1.indexOf(END_BRACKET);

        if(startBracketIndex != -1 && endBracketIndex != -1 && startBracketIndex < endBracketIndex){

            if(startBracketIndex == 0 && endBracketIndex == uriPart1.length() - 1){
                return true;
            }

            int partCharIndex = 0;
            int otherPartCharIndex = 0;

            while(otherPartCharIndex < uriPart2.length()){
                if(uriPart1.charAt(partCharIndex) == START_BRACKET){
                    do{
                        partCharIndex += 1;
                    }while(uriPart1.charAt(partCharIndex) != END_BRACKET &&
                            partCharIndex < uriPart1.length());


                    if(partCharIndex == uriPart1.length() - 1){
                        return true;
                    }

                    partCharIndex++;

                    while(otherPartCharIndex < uriPart2.length() &&
                            uriPart1.charAt(partCharIndex) != uriPart2.charAt(otherPartCharIndex)) {
                        otherPartCharIndex += 1;
                    }
                }

                if(uriPart1.charAt(partCharIndex) != uriPart2.charAt(otherPartCharIndex)){
                    return false;
                }

                partCharIndex += 1;
                otherPartCharIndex += 1;
            }
            return true;
        } else if(uriPart1.equalsIgnoreCase(uriPart2)){
            return true;
        }

        return false;
    }



}
