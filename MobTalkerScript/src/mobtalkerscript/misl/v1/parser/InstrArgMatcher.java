package mobtalkerscript.misl.v1.parser;

public class InstrArgMatcher
{
    
    private final String _source;
    
    private int _index;
    private String _match;
    
    // ========================================
    
    public InstrArgMatcher(String source)
    {
        _source = source;
    }
    
    // ========================================
    
    public boolean find()
    {
        boolean found = false;
        
        // Start
        int limit = _source.length();
        for (; _index < limit; _index++)
        {
            if (isIdentifier())
            {
                break;
            }
        }
        
        if (_index == limit)
        {
            return false;
        }
        
        if (isString())
        {
            found = parseString();
        }
        else if (isNumber())
        {
            found = parseNumber();
        }
        else if (isIdentifier())
        {
            found = parseIdentifier();
        }
        
        return found;
    }
    
    public String getMatch()
    {
        return _match;
    }
    
    private boolean isString()
    {
        return _source.charAt(_index) == '"';
    }
    
    private boolean parseString()
    {
        int start = _index++;
        int end = -1;
        
        for (int limit = _source.length(); _index < limit; _index++)
        {
            if ((_source.charAt(_index) == '"') && !isEscaped())
            {
                end = ++_index;
                _match = _source.substring(start, end).replace("\\", "");
                
                break;
            }
        }
        
        return end > -1;
    }
    
    private boolean isEscaped()
    {
        return _source.charAt(_index - 1) == 92;
    }
    
    private boolean isNumber()
    {
        return (('0' <= _source.charAt(_index)) && (_source.charAt(_index) <= '9'))
               || ((_source.charAt(_index) == '-') && ('0' <= _source.charAt(_index + 1)) && (_source.charAt(_index + 1) <= '9'));
    }
    
    private boolean parseNumber()
    {
        int start = _index;
        int end = _index + 1;
        boolean hasDecimalPoint = false;
        
        if (_source.charAt(_index) == '-')
        {
            _index++;
        }
        
        int limit = _source.length();
        for (; _index < limit; _index++)
        {
            if (_source.charAt(_index) == '.')
            {
                if (hasDecimalPoint)
                {
                    break;
                }
                
                hasDecimalPoint = true;
                _index++;
            }
            
            if ((_source.charAt(_index) < '0') || ('9' < _source.charAt(_index)))
            {
                break;
            }
            else
            {
                end = _index + 1;
            }
            
        }
        
        _match = _source.substring(start, end);
        
        return end > -1;
    }
    
    private boolean isIdentifier()
    {
        return (33 <= _source.charAt(_index)) && (_source.charAt(_index) <= 126);
    }
    
    private boolean parseIdentifier()
    {
        int start = _index;
        
        int limit = _source.length();
        for (; _index < limit; _index++)
        {
            if (!isIdentifier())
            {
                break;
            }
        }
        
        _match = _source.substring(start, _index);
        
        return true;
    }
    
}
