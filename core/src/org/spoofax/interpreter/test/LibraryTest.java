/*
 * Created on 05.jul.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the IBM Common Public License, v1.0
 */
package org.spoofax.interpreter.test;


public class LibraryTest extends InterpreterTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp("/home/karltk/source/oss/spoofax/spoofax/core/tests/data/library");
    }
    
    public void testTuple() {  interpTest("tuple-test", "[2,0]"); }
    public void testTemplate() {  interpTest("template-test", "[1,0]"); }
    public void testReals() {  interpTest("reals-test", "[8,0]"); }
    public void testListSet() { interpTest("list-set-test", "[25,0]"); }
    public void testListZip() { interpTest("list-zip-test", "[3,0]"); }
    public void testIntList() { interpTest("int-list-test", "[15,0]"); }
    public void testListMisc() { interpTest("list-misc-test", "[23,0]"); }
    public void testListBasic() { interpTest("list-basic-test" ,"[17,0]"); }
    public void testListIndex() { interpTest("list-index-test", "[17,0]"); }
    
/*    public void testPOSIXError() {  interpTest("posix-error-test");   }
    
    public void testPlaceholder() {  interpTest("placeholder-test");   }
    public void testSubstitution() {  interpTest("substitution-test");   }
    public void testListFilter() {  interpTest("lister-filter-test");   }
    public void testUnification() { interpTest("unification-test"); } 
    public void testIO() { interpTest("io-test"); }
    public void testDir() { interpTest("dir-test"); }
    public void testEnvTraversal() { interpTest("env-traversal-test"); }
    public void testRename() { interpTest("rename-test"); }
    public void testShare() { interpTest("share-test"); }
    public void testIteration() { interpTest("iteration-test"); }
    public void testApply() { interpTest("apply-test"); }
    public void testSets() { interpTest("sets-test"); }
    public void testPOSIXFile() { interpTest("posix-file-test"); }
    public void testPOSIXProcess() { interpTest("posix-process-test"); }
    public void testTermZip() { interpTest("term-zip-test"); }
    public void testScopedFiniteMap() { interpTest("scoped-finite-map-test"); }
    public void testStrcmp() { interpTest("strcmp-test"); }
    public void testSimpleTraversal() { interpTest("simple-traversal-test"); }
    public void testCollect() { interpTest("collect-test"); }
    public void testStringMisc() { interpTest("string-misc-test"); }
    public void testIntegers() { interpTest("integers-test"); }
    public void testTime() { interpTest("time-test"); }
    public void testParenthesize() { interpTest("parenthesize-test"); }
    public void testParseOptions() { interpTest("parse-options-test"); }
    public void testString() { interpTest("string-test"); }
    public void testFile() { interpTest("file-test"); }
    public void testTables() { interpTest("tables-test"); }
    public void testDynamicRulesLowlevel() { interpTest("dynamic-rules-lowlevel-test"); }
    public void testDynamicRulesHighlevel() { interpTest("dynamic-rules-highlevel-test"); }
*/

    public void interpTest(String test, String result) {
        super.interpTest(test, itp.makeTuple("[]"), itp.makeTuple(result));
    }
}

