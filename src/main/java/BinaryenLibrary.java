import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import java.util.List;
import java.util.Arrays;

public interface BinaryenLibrary extends Library {
	BinaryenLibrary INSTANCE = (BinaryenLibrary) Native.load("binaryen", BinaryenLibrary.class);

	@Structure.FieldOrder({ "type", "value" })
	public static class BinaryenLiteral extends Structure {
		public static class ByValue extends BinaryenLiteral implements Structure.ByValue {
		}

		public long type;
		public ValueUnion value;

		public static class ValueUnion extends Union {
			public int i32;
			public long i64;
			public float f32;
			public double f64;
			public byte[] v128 = new byte[16];
			public Pointer func; // Use Pointer for const char*
		}
	}

	// BINARYEN_API BinaryenModuleRef BinaryenModuleCreate(void);
	Pointer BinaryenModuleCreate();

	// BINARYEN_API void BinaryenModuleDispose(BinaryenModuleRef module);
	void BinaryenModuleDispose(Pointer module);

	int BinaryenTypeCreate(PointerByReference valueTypes, int numTypes);

	int BinaryenAddInt32();

	int BinaryenSubInt32();

	int BinaryenMulInt32();

	int BinaryenTypeNone();

	int BinaryenTypeInt32();

	int BinaryenTypeInt64();

	int BinaryenTypeFloat32();

	int BinaryenTypeFloat64();

	BinaryenLiteral.ByValue BinaryenLiteralInt32(int x);

	BinaryenLiteral.ByValue BinaryenLiteralInt64(long x);

	BinaryenLiteral.ByValue BinaryenLiteralFloat32(float x);

	BinaryenLiteral.ByValue BinaryenLiteralFloat64(double x);

	Pointer BinaryenLocalGet(Pointer module, int index, int type);

	Pointer BinaryenLocalSet(Pointer module, int index, Pointer value);

	Pointer BinaryenLocalTee(Pointer module, int index, Pointer value, int type);

	Pointer BinaryenGlobalGet(Pointer module, String name, int type);

	Pointer BinaryenGlobalSet(Pointer module, String name, Pointer value);

	Pointer BinaryenConst(Pointer module, BinaryenLiteral.ByValue value);

	Pointer BinaryenUnary(Pointer module, int op, Pointer value);

	Pointer BinaryenBinary(Pointer module, int op, Pointer left, Pointer right);

	Pointer BinaryenAddFunction(Pointer module, String name, int params, int results, Pointer varTypes, int numVarTypes,
			Pointer body);

	Pointer BinaryenAddFunctionWithHeapType(Pointer module, String name, int type, Pointer varTypes, int numVarTypes,
			Pointer body);

	void BinaryenModulePrint(Pointer module);
}
