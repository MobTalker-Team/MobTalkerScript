-- SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
--
-- SPDX-License-Identifier: LGPL-3.0-or-later

;;print('testing __eq meta-method');;

C1 = {}
function C1:new()
  local inst = {}
  setmetatable(inst, {
    class = 'C1',
    __index = self,
    __eq = function (a,b)
      return true
    end
  })
  return inst
end

C2 = {}
function C2:new()
  local inst = {}
  setmetatable(inst, {
    class = 'C2',
    __index = self,
    __eq = function (a,b)
      return false
    end
  })
  return inst
end

equals = function (a,b)
  return true
end

C3 = {}
function C3:new()
  local inst = {}
  setmetatable(inst, {
    class = 'C3',
    __index = self,
    __eq = equals
  })
  return inst
end

C4 = {}
function C4:new()
  local inst = {}
  setmetatable(inst, {
    class = 'C4',
    __index = self,
    __eq = equals
  })
  return inst
end

inst1 = C1.new()
inst2 = C2.new()
inst3 = C3.new()
inst4 = C4.new()

assert(inst1 == inst1, 'inst1 == inst1 yielded false, expected true')
print('+')

assert(inst2 == inst2, 'inst2 == inst2 yielded false, expected true')
print('+')

assert(inst3 == inst3, 'inst3 == inst3 yielded false, expected true')
print('+')

assert(inst4 == inst4, 'inst4 == inst4 yielded false, expected true')
print('+')

assert(inst1 == inst2, 'inst1 == inst2 yielded false, expected true')
print('+')

assert(not(inst2 == inst1), 'inst2 == inst1 yielded true, expected false')
print('+')

assert(inst1 == inst3, 'inst1 == inst3 yielded false, expected true')
print('+')

assert(inst3 == inst1, 'inst3 == inst1 yielded false, expected true')
print('+')

assert(not(inst2 == inst3), 'inst2 == inst3 yielded true, expected false')
print('+')

assert(inst3 == inst2, 'inst3 == inst2 yielded false, expected true')
print('+')

assert(inst3 == inst4, 'inst3 == inst4 yielded false, expected true')
print('+')

assert(inst4 == inst3, 'inst4 == inst3 yielded false, expected true')
print('+')

;;print('OK');;
