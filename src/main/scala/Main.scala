@main def hello: Unit =
  System.setProperty("jna.library.path", "/opt/homebrew/lib/")
  incr()

def incr(): Unit =
  val b = BinaryenLibrary.INSTANCE
  val module = b.BinaryenModuleCreate()

  // val params = b.BinaryenTypeCreate(Array(b.BinaryenTypeInt32()), 1)
  val params = b.BinaryenTypeInt32()
  val result = b.BinaryenTypeInt32()

  val x = b.BinaryenLocalGet(module, 0, b.BinaryenTypeInt32())
  val one = b.BinaryenConst(module, b.BinaryenLiteralInt32(1))
  val add = b.BinaryenBinary(module, b.BinaryenAddInt32(), x, one)

  b.BinaryenAddFunction(module, "adder", params, result, null, 0, add)

  b.BinaryenModulePrint(module)
  b.BinaryenModuleDispose(module)

