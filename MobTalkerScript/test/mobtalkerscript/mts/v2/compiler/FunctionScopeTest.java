package mobtalkerscript.mts.v2.compiler;

import static org.junit.Assert.*;

import org.junit.*;

public class FunctionScopeTest
{
    private FunctionScope _func1;
    private FunctionScope _func2;
    private FunctionScope _func3;
    
    @Before
    public void setUp() throws Exception
    {
        _func1 = new FunctionScope( null, "f1", "test" );
        _func2 = new FunctionScope( _func1, "f2", "test" );
        _func3 = new FunctionScope( _func2, "f3", "test" );
    }
    
    @Test
    public void testEnterBlock()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testLeaveBlock()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testGetConstantIndex()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testIsLocal()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testDeclareLocal()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testGetLocalIndexStringInt()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testGetExternalIndex()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testIsExternal()
    {
        fail( "Not yet implemented" );
    }
    
}
