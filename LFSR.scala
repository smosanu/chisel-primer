package projPrimavara

import Chisel._

class LFSR extends Module {
  //declare input-output interface signals
  val io = new Bundle {
    //dummy input signal not used
    val dummy = Bool(INPUT)
    //clock and reset are default, out will have the random value
    val out = UInt(OUTPUT, 6)
    val out3 = UInt(OUTPUT, 3)
  }

  //declare the 6-bit register and initialize to 16'd1
  val D0123456 = Reg(init = UInt(1, 6)) //will init at reset

  //next clk value is XOR of 2 MSBs as LSB concatenated with left shift rest
  //example: 010010 => 100101 = Cat('10010','1')
  val nxt_D0123456 = Cat(D0123456(4,0),D0123456(5)^D0123456(4))
  //update 6-bit register will happen in sync with clk
  D0123456 := nxt_D0123456
  //assign output (reverse order just for fun)
  io.out := D0123456
  io.out3 := Cat(D0123456(1),D0123456(3),D0123456(5))
}


class LFSRTests(c: LFSR) extends Tester(c) {
  //init D0123456 to 1
  var D0123456 = 1
  for (t <- 0 until 63) {
    step(1)
    val bit = ((D0123456 >> 5) ^ (D0123456 >> 4)) & 1;
    D0123456 = (D0123456 << 1) | bit;
    if(D0123456>63){
      D0123456 = D0123456 - 64
    }
    println(bit, D0123456)
    expect(c.io.out, D0123456)
  }
}