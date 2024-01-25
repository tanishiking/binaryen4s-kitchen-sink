import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference

@main def hello: Unit =
  System.setProperty("jna.library.path", "/opt/homebrew/lib/")
  incr()
  // struct()

def struct(): Unit = ???
  // val b = BinaryenLibrary.INSTANCE
  // val module = b.BinaryenModuleCreate()

  // val tb = b.TypeBuilderCreate(10);

  // // Set signature type at index
  // b.TypeBuilderSetSignatureType(tb, 0, b.BinaryenTypeInt32(), b.BinaryenTypeInt32());

  // val fieldTypes = Array(b.BinaryenTypeFloat32(), b.BinaryenTypeFloat64())
  // val fieldPackedTypes = Array.emptyIntArray
  // val fieldMutables = Array(true, false)
  // b.TypeBuilderSetStructType(tb, 1, fieldTypes, fieldPackedTypes, fieldMutables, 2);

  // val heapTypes = new Array[Int](1);
  // val errorIndex = new IntByReference();
  // val errorReason = new IntByReference();

  // println("build and dispose")
  // val success = b.TypeBuilderBuildAndDispose(tb, heapTypes, errorIndex, errorReason);

  // if (!success) {
  //   println(errorReason.getValue())
  //   println(errorIndex.getValue())
  // } 

  // b.BinaryenModulePrint(module)
  // b.BinaryenModuleDispose(module)



def incr(): Unit =
  val b = BinaryenLibrary.INSTANCE
  val module: BinaryenLibrary.BinaryenModuleRef = b.BinaryenModuleCreate()

  // val params = b.BinaryenTypeCreate(Array(b.BinaryenTypeInt32()), 1)
  val params: BinaryenLibrary.BinaryenType = b.BinaryenTypeInt64()
  val result = b.BinaryenTypeInt64()

  val x = b.BinaryenLocalGet(module, 0, b.BinaryenTypeInt64())
  val one = b.BinaryenConst(module, b.BinaryenLiteralInt64(1))
  val add = b.BinaryenBinary(module, b.BinaryenAddInt64(), x, one)

  b.BinaryenAddFunction(module, "adder", params, result, null, 0, add)
  b.BinaryenAddFunctionExport(module, "adder", "adder")

  b.BinaryenModulePrint(module)
  b.BinaryenModuleDispose(module)

