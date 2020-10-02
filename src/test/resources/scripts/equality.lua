-- SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
--
-- SPDX-License-Identifier: LGPL-3.0-or-later

;;print('testing __eq meta-method');;

MyClass = {}
function MyClass:new()
  local newInstance = {}
  setmetatable(newInstance, {
    __index = self,
    __eq = function(a, b)
      return true
    end
  })
  return newInstance
end

MyOtherClass = {}
function MyOtherClass:new()
  local newInstance = {}
  setmetatable(newInstance, {
    __index = self,
    __eq = function(a, b)
      return true
    end
  })
  return newInstance
end

instA = MyClass.new()
instB = MyClass.new()
otherInstA = MyOtherClass.new()

assert(instA == instA, 'instA does not equal instA')
print('+')

assert(instA == instB, 'instA does not equal instB')
print('+')

assert(instB == instA, 'instB does not equal instA')
print('+')

assert(instA ~= otherInstA, 'instA equals otherInstA')
print('+')

assert(otherInstA ~= instA, 'otherInstA equals instA')
print('+')

;;print('OK');;