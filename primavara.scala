// See LICENSE.txt for license details.
package projPrimavara

import Chisel._

object projPrimavara {
  def main(args: Array[String]): Unit = { 
    val tutArgs = args.slice(1, args.length) 
    val res = 
    args(0) match {
      case "Mux2" => 
        chiselMainTest(tutArgs, () => Module(new Mux2())){
          c => new Mux2Tests(c)}
      case "LFSR" => 
        chiselMainTest(tutArgs, () => Module(new LFSR())){
          c => new LFSRTests(c)}
    }
  }
}

