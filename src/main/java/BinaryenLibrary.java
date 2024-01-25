import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;
import java.util.List;
import java.util.Arrays;

public interface BinaryenLibrary extends Library {
    BinaryenLibrary INSTANCE = (BinaryenLibrary) Native.load("binaryen", BinaryenLibrary.class);

    // public static class int extends int {};
    // typedef uintptr_t BinaryenType
    public static class BinaryenType extends IntegerType {
        public BinaryenType() { super(4, true); }
    };
    // typedef uintptr_t BinaryenHeapType
    public static class BinaryenHeapType extends IntegerType {
        public BinaryenHeapType() { super(4, true); }
    };

    // typedef uint32_t BinaryenHeapType
    public static class BinaryenPackedType extends IntegerType {
        public BinaryenPackedType() { super(4, true); }
    };

    BinaryenType BinaryenTypeNone();
    BinaryenType BinaryenTypeInt32();
    BinaryenType BinaryenTypeInt64();
    BinaryenType BinaryenTypeFloat32();
    BinaryenType BinaryenTypeFloat64();
    BinaryenType BinaryenTypeVec128();
    BinaryenType BinaryenTypeFuncref();
    BinaryenType BinaryenTypeExternref();
    BinaryenType BinaryenTypeAnyref();
    BinaryenType BinaryenTypeEqref();
    BinaryenType BinaryenTypeI31ref();
    BinaryenType BinaryenTypeStructref();
    BinaryenType BinaryenTypeArrayref();
    BinaryenType BinaryenTypeStringref();
    BinaryenType BinaryenTypeStringviewWTF8();
    BinaryenType BinaryenTypeStringviewWTF16();
    BinaryenType BinaryenTypeStringviewIter();
    BinaryenType BinaryenTypeNullref();
    BinaryenType BinaryenTypeNullExternref();
    BinaryenType BinaryenTypeNullFuncref();
    BinaryenType BinaryenTypeUnreachable();
    BinaryenType BinaryenTypeAuto();
    BinaryenType BinaryenTypeCreate(int[] valueTypes, int numTypes);
    BinaryenType BinaryenTypeArity(int t);
    void intExpand(int t, int[] buf);

    // Packed Types
    BinaryenPackedType BinaryenPackedTypeNotPacked();
    BinaryenPackedType BinaryenPackedTypeInt8();
    BinaryenPackedType BinaryenPackedTypeInt16();

    // Heap Types
    BinaryenHeapType BinaryenHeapTypeExt();
    BinaryenHeapType BinaryenHeapTypeFunc();
    BinaryenHeapType BinaryenHeapTypeAny();
    BinaryenHeapType BinaryenHeapTypeEq();
    BinaryenHeapType BinaryenHeapTypeI31();
    BinaryenHeapType BinaryenHeapTypeStruct();
    BinaryenHeapType BinaryenHeapTypeArray();
    BinaryenHeapType BinaryenHeapTypeString();
    BinaryenHeapType BinaryenHeapTypeStringviewWTF8();
    BinaryenHeapType BinaryenHeapTypeStringviewWTF16();
    BinaryenHeapType BinaryenHeapTypeStringviewIter();
    BinaryenHeapType BinaryenHeapTypeNone();
    BinaryenHeapType BinaryenHeapTypeNoext();
    BinaryenHeapType BinaryenHeapTypeNofunc();

    int BinaryenHeapTypeIsBasic(BinaryenHeapType heapType);
    int BinaryenHeapTypeIsSignature(BinaryenHeapType heapType);
    int BinaryenHeapTypeIsStruct(BinaryenHeapType heapType);
    int BinaryenHeapTypeIsArray(BinaryenHeapType heapType);
    int BinaryenHeapTypeIsBottom(BinaryenHeapType heapType);
    int BinaryenHeapTypeGetBottom(BinaryenHeapType heapType);

    int BinaryenHeapTypeIsSubType(int left, int right);
    int BinaryenStructTypeGetNumFields(BinaryenHeapType heapType);
    int BinaryenStructTypeGetFieldType(BinaryenHeapType heapType, int index);
    int BinaryenStructTypeGetFieldPackedType(BinaryenHeapType heapType, int index);
    int BinaryenStructTypeIsFieldMutable(BinaryenHeapType heapType, int index);

    int BinaryenArrayTypeGetElementType(BinaryenHeapType heapType);
    int BinaryenArrayTypeGetElementPackedType(BinaryenHeapType heapType);
    int BinaryenArrayTypeIsElementMutable(BinaryenHeapType heapType);
    int BinaryenSignatureTypeGetParams(BinaryenHeapType heapType);
    int BinaryenSignatureTypeGetResults(BinaryenHeapType heapType);

    int intGetHeapType(int type);
    int intIsNullable(int type);
    int intFromHeapType(BinaryenHeapType heapType, boolean nullable);

    // TODO: typedef uint32_t BinaryenExternalKind;
    // TODO: BinaryenFeature

    // module
    public static class BinaryenModule extends Structure {};
    public static class BinaryenModuleRef extends PointerType {};
    BinaryenModuleRef BinaryenModuleCreate();
    void BinaryenModuleDispose(BinaryenModuleRef module);

    // literal
    @Structure.FieldOrder({ "type", "value" })
    public static class BinaryenLiteral extends Structure {
        public static class ByValue extends BinaryenLiteral implements Structure.ByValue {};
        public int type;
        public ValueUnion value;
        public static class ValueUnion extends Union {
            public int i32;
            public int i64;
            public float f32;
            public double f64;
            public byte[] v128 = new byte[16];
            public Pointer func; // Use Pointer for const char*
        }
    }
    BinaryenLiteral.ByValue BinaryenLiteralInt32(int x);
    BinaryenLiteral.ByValue BinaryenLiteralInt64(int x);
    BinaryenLiteral.ByValue BinaryenLiteralFloat32(float x);
    BinaryenLiteral.ByValue BinaryenLiteralFloat64(double x);
    BinaryenLiteral.ByValue BinaryenLiteralVec128(byte[] x);
    BinaryenLiteral.ByValue BinaryenLiteralFloat32Bits(int x);
    BinaryenLiteral.ByValue BinaryenLiteralFloat64Bits(int x);

    // Expressions
    //
    // Some expressions have a BinaryenOp, which is the more
    // specific operation/opcode.
    //
    // Some expressions have optional parameters, like Return may not
    // return a value. You can supply a NULL pointer in those cases.
    //
    // For more information, see wasm.h
    int BinaryenCtzInt32();
    int BinaryenPopcntInt32();
    int BinaryenNegFloat32();
    int BinaryenAbsFloat32();
    int BinaryenCeilFloat32();
    int BinaryenFloorFloat32();
    int BinaryenTruncFloat32();
    int BinaryenNearestFloat32();
    int BinaryenSqrtFloat32();
    int BinaryenEqZInt32();
    int BinaryenClzInt64();
    int BinaryenCtzInt64();
    int BinaryenPopcntInt64();
    int BinaryenNegFloat64();
    int BinaryenAbsFloat64();
    int BinaryenCeilFloat64();
    int BinaryenFloorFloat64();
    int BinaryenTruncFloat64();
    int BinaryenNearestFloat64();
    int BinaryenSqrtFloat64();
    int BinaryenEqZInt64();
    int BinaryenExtendSInt32();
    int BinaryenExtendUInt32();
    int BinaryenWrapInt64();
    int BinaryenTruncSFloat32ToInt32();
    int BinaryenTruncSFloat32ToInt64();
    int BinaryenTruncUFloat32ToInt32();
    int BinaryenTruncUFloat32ToInt64();
    int BinaryenTruncSFloat64ToInt32();
    int BinaryenTruncSFloat64ToInt64();
    int BinaryenTruncUFloat64ToInt32();
    int BinaryenTruncUFloat64ToInt64();
    int BinaryenReinterpretFloat32();
    int BinaryenReinterpretFloat64();
    int BinaryenConvertSInt32ToFloat32();
    int BinaryenConvertSInt32ToFloat64();
    int BinaryenConvertUInt32ToFloat32();
    int BinaryenConvertUInt32ToFloat64();
    int BinaryenConvertSInt64ToFloat32();
    int BinaryenConvertSInt64ToFloat64();
    int BinaryenConvertUInt64ToFloat32();
    int BinaryenConvertUInt64ToFloat64();
    int BinaryenPromoteFloat32();
    int BinaryenDemoteFloat64();
    int BinaryenReinterpretInt32();
    int BinaryenReinterpretInt64();
    int BinaryenExtendS8Int32();
    int BinaryenExtendS16Int32();
    int BinaryenExtendS8Int64();
    int BinaryenExtendS16Int64();
    int BinaryenExtendS32Int64();
    int BinaryenAddInt32();
    int BinaryenSubInt32();
    int BinaryenMulInt32();
    int BinaryenDivSInt32();
    int BinaryenDivUInt32();
    int BinaryenRemSInt32();
    int BinaryenRemUInt32();
    int BinaryenAndInt32();
    int BinaryenOrInt32();
    int BinaryenXorInt32();
    int BinaryenShlInt32();
    int BinaryenShrUInt32();
    int BinaryenShrSInt32();
    int BinaryenRotLInt32();
    int BinaryenRotRInt32();
    int BinaryenEqInt32();
    int BinaryenNeInt32();
    int BinaryenLtSInt32();
    int BinaryenLtUInt32();
    int BinaryenLeSInt32();
    int BinaryenLeUInt32();
    int BinaryenGtSInt32();
    int BinaryenGtUInt32();
    int BinaryenGeSInt32();
    int BinaryenGeUInt32();
    int BinaryenAddInt64();
    int BinaryenSubInt64();
    int BinaryenMulInt64();
    int BinaryenDivSInt64();
    int BinaryenDivUInt64();
    int BinaryenRemSInt64();
    int BinaryenRemUInt64();
    int BinaryenAndInt64();
    int BinaryenOrInt64();
    int BinaryenXorInt64();
    int BinaryenShlInt64();
    int BinaryenShrUInt64();
    int BinaryenShrSInt64();
    int BinaryenRotLInt64();
    int BinaryenRotRInt64();
    int BinaryenEqInt64();
    int BinaryenNeInt64();
    int BinaryenLtSInt64();
    int BinaryenLtUInt64();
    int BinaryenLeSInt64();
    int BinaryenLeUInt64();
    int BinaryenGtSInt64();
    int BinaryenGtUInt64();
    int BinaryenGeSInt64();
    int BinaryenGeUInt64();
    int BinaryenAddFloat32();
    int BinaryenSubFloat32();
    int BinaryenMulFloat32();
    int BinaryenDivFloat32();
    int BinaryenCopySignFloat32();
    int BinaryenMinFloat32();
    int BinaryenMaxFloat32();
    int BinaryenEqFloat32();
    int BinaryenNeFloat32();
    int BinaryenLtFloat32();
    int BinaryenLeFloat32();
    int BinaryenGtFloat32();
    int BinaryenGeFloat32();
    int BinaryenAddFloat64();
    int BinaryenSubFloat64();
    int BinaryenMulFloat64();
    int BinaryenDivFloat64();
    int BinaryenCopySignFloat64();
    int BinaryenMinFloat64();
    int BinaryenMaxFloat64();
    int BinaryenEqFloat64();
    int BinaryenNeFloat64();
    int BinaryenLtFloat64();
    int BinaryenLeFloat64();
    int BinaryenGtFloat64();
    int BinaryenGeFloat64();
    int BinaryenAtomicRMWAdd();
    int BinaryenAtomicRMWSub();
    int BinaryenAtomicRMWAnd();
    int BinaryenAtomicRMWOr();
    int BinaryenAtomicRMWXor();
    int BinaryenAtomicRMWXchg();
    int BinaryenTruncSatSFloat32ToInt32();
    int BinaryenTruncSatSFloat32ToInt64();
    int BinaryenTruncSatUFloat32ToInt32();
    int BinaryenTruncSatUFloat32ToInt64();
    int BinaryenTruncSatSFloat64ToInt32();
    int BinaryenTruncSatSFloat64ToInt64();
    int BinaryenTruncSatUFloat64ToInt32();
    int BinaryenTruncSatUFloat64ToInt64();
    int BinaryenSplatVecI8x16();
    int BinaryenExtractLaneSVecI8x16();
    int BinaryenExtractLaneUVecI8x16();
    int BinaryenReplaceLaneVecI8x16();
    int BinaryenSplatVecI16x8();
    int BinaryenExtractLaneSVecI16x8();
    int BinaryenExtractLaneUVecI16x8();
    int BinaryenReplaceLaneVecI16x8();
    int BinaryenSplatVecI32x4();
    int BinaryenExtractLaneVecI32x4();
    int BinaryenReplaceLaneVecI32x4();
    int BinaryenSplatVecI64x2();
    int BinaryenExtractLaneVecI64x2();
    int BinaryenReplaceLaneVecI64x2();
    int BinaryenSplatVecF32x4();
    int BinaryenExtractLaneVecF32x4();
    int BinaryenReplaceLaneVecF32x4();
    int BinaryenSplatVecF64x2();
    int BinaryenExtractLaneVecF64x2();
    int BinaryenReplaceLaneVecF64x2();
    int BinaryenEqVecI8x16();
    int BinaryenNeVecI8x16();
    int BinaryenLtSVecI8x16();
    int BinaryenLtUVecI8x16();
    int BinaryenGtSVecI8x16();
    int BinaryenGtUVecI8x16();
    int BinaryenLeSVecI8x16();
    int BinaryenLeUVecI8x16();
    int BinaryenGeSVecI8x16();
    int BinaryenGeUVecI8x16();
    int BinaryenEqVecI16x8();
    int BinaryenNeVecI16x8();
    int BinaryenLtSVecI16x8();
    int BinaryenLtUVecI16x8();
    int BinaryenGtSVecI16x8();
    int BinaryenGtUVecI16x8();
    int BinaryenLeSVecI16x8();
    int BinaryenLeUVecI16x8();
    int BinaryenGeSVecI16x8();
    int BinaryenGeUVecI16x8();
    int BinaryenEqVecI32x4();
    int BinaryenNeVecI32x4();
    int BinaryenLtSVecI32x4();
    int BinaryenLtUVecI32x4();
    int BinaryenGtSVecI32x4();
    int BinaryenGtUVecI32x4();
    int BinaryenLeSVecI32x4();
    int BinaryenLeUVecI32x4();
    int BinaryenGeSVecI32x4();
    int BinaryenGeUVecI32x4();
    int BinaryenEqVecI64x2();
    int BinaryenNeVecI64x2();
    int BinaryenLtSVecI64x2();
    int BinaryenGtSVecI64x2();
    int BinaryenLeSVecI64x2();
    int BinaryenGeSVecI64x2();
    int BinaryenEqVecF32x4();
    int BinaryenNeVecF32x4();
    int BinaryenLtVecF32x4();
    int BinaryenGtVecF32x4();
    int BinaryenLeVecF32x4();
    int BinaryenGeVecF32x4();
    int BinaryenEqVecF64x2();
    int BinaryenNeVecF64x2();
    int BinaryenLtVecF64x2();
    int BinaryenGtVecF64x2();
    int BinaryenLeVecF64x2();
    int BinaryenGeVecF64x2();
    int BinaryenNotVec128();
    int BinaryenAndVec128();
    int BinaryenOrVec128();
    int BinaryenXorVec128();
    int BinaryenAndNotVec128();
    int BinaryenBitselectVec128();
    int BinaryenRelaxedFmaVecF32x4();
    int BinaryenRelaxedFmsVecF32x4();
    int BinaryenRelaxedFmaVecF64x2();
    int BinaryenRelaxedFmsVecF64x2();
    int BinaryenLaneselectI8x16();
    int BinaryenLaneselectI16x8();
    int BinaryenLaneselectI32x4();
    int BinaryenLaneselectI64x2();
    int BinaryenDotI8x16I7x16AddSToVecI32x4();
    int BinaryenAnyTrueVec128();
    int BinaryenPopcntVecI8x16();
    int BinaryenAbsVecI8x16();
    int BinaryenNegVecI8x16();
    int BinaryenAllTrueVecI8x16();
    int BinaryenBitmaskVecI8x16();
    int BinaryenShlVecI8x16();
    int BinaryenShrSVecI8x16();
    int BinaryenShrUVecI8x16();
    int BinaryenAddVecI8x16();
    int BinaryenAddSatSVecI8x16();
    int BinaryenAddSatUVecI8x16();
    int BinaryenSubVecI8x16();
    int BinaryenSubSatSVecI8x16();
    int BinaryenSubSatUVecI8x16();
    int BinaryenMinSVecI8x16();
    int BinaryenMinUVecI8x16();
    int BinaryenMaxSVecI8x16();
    int BinaryenMaxUVecI8x16();
    int BinaryenAvgrUVecI8x16();
    int BinaryenAbsVecI16x8();
    int BinaryenNegVecI16x8();
    int BinaryenAllTrueVecI16x8();
    int BinaryenBitmaskVecI16x8();
    int BinaryenShlVecI16x8();
    int BinaryenShrSVecI16x8();
    int BinaryenShrUVecI16x8();
    int BinaryenAddVecI16x8();
    int BinaryenAddSatSVecI16x8();
    int BinaryenAddSatUVecI16x8();
    int BinaryenSubVecI16x8();
    int BinaryenSubSatSVecI16x8();
    int BinaryenSubSatUVecI16x8();
    int BinaryenMulVecI16x8();
    int BinaryenMinSVecI16x8();
    int BinaryenMinUVecI16x8();
    int BinaryenMaxSVecI16x8();
    int BinaryenMaxUVecI16x8();
    int BinaryenAvgrUVecI16x8();
    int BinaryenQ15MulrSatSVecI16x8();
    int BinaryenExtMulLowSVecI16x8();
    int BinaryenExtMulHighSVecI16x8();
    int BinaryenExtMulLowUVecI16x8();
    int BinaryenExtMulHighUVecI16x8();
    int BinaryenAbsVecI32x4();
    int BinaryenNegVecI32x4();
    int BinaryenAllTrueVecI32x4();
    int BinaryenBitmaskVecI32x4();
    int BinaryenShlVecI32x4();
    int BinaryenShrSVecI32x4();
    int BinaryenShrUVecI32x4();
    int BinaryenAddVecI32x4();
    int BinaryenSubVecI32x4();
    int BinaryenMulVecI32x4();
    int BinaryenMinSVecI32x4();
    int BinaryenMinUVecI32x4();
    int BinaryenMaxSVecI32x4();
    int BinaryenMaxUVecI32x4();
    int BinaryenDotSVecI16x8ToVecI32x4();
    int BinaryenExtMulLowSVecI32x4();
    int BinaryenExtMulHighSVecI32x4();
    int BinaryenExtMulLowUVecI32x4();
    int BinaryenExtMulHighUVecI32x4();
    int BinaryenAbsVecI64x2();
    int BinaryenNegVecI64x2();
    int BinaryenAllTrueVecI64x2();
    int BinaryenBitmaskVecI64x2();
    int BinaryenShlVecI64x2();
    int BinaryenShrSVecI64x2();
    int BinaryenShrUVecI64x2();
    int BinaryenAddVecI64x2();
    int BinaryenSubVecI64x2();
    int BinaryenMulVecI64x2();
    int BinaryenExtMulLowSVecI64x2();
    int BinaryenExtMulHighSVecI64x2();
    int BinaryenExtMulLowUVecI64x2();
    int BinaryenExtMulHighUVecI64x2();
    int BinaryenAbsVecF32x4();
    int BinaryenNegVecF32x4();
    int BinaryenSqrtVecF32x4();
    int BinaryenAddVecF32x4();
    int BinaryenSubVecF32x4();
    int BinaryenMulVecF32x4();
    int BinaryenDivVecF32x4();
    int BinaryenMinVecF32x4();
    int BinaryenMaxVecF32x4();
    int BinaryenPMinVecF32x4();
    int BinaryenPMaxVecF32x4();
    int BinaryenCeilVecF32x4();
    int BinaryenFloorVecF32x4();
    int BinaryenTruncVecF32x4();
    int BinaryenNearestVecF32x4();
    int BinaryenAbsVecF64x2();
    int BinaryenNegVecF64x2();
    int BinaryenSqrtVecF64x2();
    int BinaryenAddVecF64x2();
    int BinaryenSubVecF64x2();
    int BinaryenMulVecF64x2();
    int BinaryenDivVecF64x2();
    int BinaryenMinVecF64x2();
    int BinaryenMaxVecF64x2();
    int BinaryenPMinVecF64x2();
    int BinaryenPMaxVecF64x2();
    int BinaryenCeilVecF64x2();
    int BinaryenFloorVecF64x2();
    int BinaryenTruncVecF64x2();
    int BinaryenNearestVecF64x2();
    int BinaryenExtAddPairwiseSVecI8x16ToI16x8();
    int BinaryenExtAddPairwiseUVecI8x16ToI16x8();
    int BinaryenExtAddPairwiseSVecI16x8ToI32x4();
    int BinaryenExtAddPairwiseUVecI16x8ToI32x4();
    int BinaryenTruncSatSVecF32x4ToVecI32x4();
    int BinaryenTruncSatUVecF32x4ToVecI32x4();
    int BinaryenConvertSVecI32x4ToVecF32x4();
    int BinaryenConvertUVecI32x4ToVecF32x4();
    int BinaryenLoad8SplatVec128();
    int BinaryenLoad16SplatVec128();
    int BinaryenLoad32SplatVec128();
    int BinaryenLoad64SplatVec128();
    int BinaryenLoad8x8SVec128();
    int BinaryenLoad8x8UVec128();
    int BinaryenLoad16x4SVec128();
    int BinaryenLoad16x4UVec128();
    int BinaryenLoad32x2SVec128();
    int BinaryenLoad32x2UVec128();
    int BinaryenLoad32ZeroVec128();
    int BinaryenLoad64ZeroVec128();
    int BinaryenLoad8LaneVec128();
    int BinaryenLoad16LaneVec128();
    int BinaryenLoad32LaneVec128();
    int BinaryenLoad64LaneVec128();
    int BinaryenStore8LaneVec128();
    int BinaryenStore16LaneVec128();
    int BinaryenStore32LaneVec128();
    int BinaryenStore64LaneVec128();
    int BinaryenNarrowSVecI16x8ToVecI8x16();
    int BinaryenNarrowUVecI16x8ToVecI8x16();
    int BinaryenNarrowSVecI32x4ToVecI16x8();
    int BinaryenNarrowUVecI32x4ToVecI16x8();
    int BinaryenExtendLowSVecI8x16ToVecI16x8();
    int BinaryenExtendHighSVecI8x16ToVecI16x8();
    int BinaryenExtendLowUVecI8x16ToVecI16x8();
    int BinaryenExtendHighUVecI8x16ToVecI16x8();
    int BinaryenExtendLowSVecI16x8ToVecI32x4();
    int BinaryenExtendHighSVecI16x8ToVecI32x4();
    int BinaryenExtendLowUVecI16x8ToVecI32x4();
    int BinaryenExtendHighUVecI16x8ToVecI32x4();
    int BinaryenExtendLowSVecI32x4ToVecI64x2();
    int BinaryenExtendHighSVecI32x4ToVecI64x2();
    int BinaryenExtendLowUVecI32x4ToVecI64x2();
    int BinaryenExtendHighUVecI32x4ToVecI64x2();
    int BinaryenConvertLowSVecI32x4ToVecF64x2();
    int BinaryenConvertLowUVecI32x4ToVecF64x2();
    int BinaryenTruncSatZeroSVecF64x2ToVecI32x4();
    int BinaryenTruncSatZeroUVecF64x2ToVecI32x4();
    int BinaryenDemoteZeroVecF64x2ToVecF32x4();
    int BinaryenPromoteLowVecF32x4ToVecF64x2();
    int BinaryenRelaxedTruncSVecF32x4ToVecI32x4();
    int BinaryenRelaxedTruncUVecF32x4ToVecI32x4();
    int BinaryenRelaxedTruncZeroSVecF64x2ToVecI32x4();
    int BinaryenRelaxedTruncZeroUVecF64x2ToVecI32x4();
    int BinaryenSwizzleVecI8x16();
    int BinaryenRelaxedSwizzleVecI8x16();
    int BinaryenRelaxedMinVecF32x4();
    int BinaryenRelaxedMaxVecF32x4();
    int BinaryenRelaxedMinVecF64x2();
    int BinaryenRelaxedMaxVecF64x2();
    int BinaryenRelaxedQ15MulrSVecI16x8();
    int BinaryenDotI8x16I7x16SToVecI16x8();
    int BinaryenRefAsNonNull();
    int BinaryenRefAsExternInternalize();
    int BinaryenRefAsExternExternalize();
    int BinaryenBrOnNull();
    int BinaryenBrOnNonNull();
    int BinaryenBrOnCast();
    int BinaryenBrOnCastFail();
    int BinaryenStringNewUTF8();
    int BinaryenStringNewWTF8();
    int BinaryenStringNewLossyUTF8();
    int BinaryenStringNewWTF16();
    int BinaryenStringNewUTF8Array();
    int BinaryenStringNewWTF8Array();
    int BinaryenStringNewLossyUTF8Array();
    int BinaryenStringNewWTF16Array();
    int BinaryenStringNewFromCodePoint();
    int BinaryenStringMeasureUTF8();
    int BinaryenStringMeasureWTF8();
    int BinaryenStringMeasureWTF16();
    int BinaryenStringMeasureIsUSV();
    int BinaryenStringMeasureWTF16View();
    int BinaryenStringEncodeUTF8();
    int BinaryenStringEncodeLossyUTF8();
    int BinaryenStringEncodeWTF8();
    int BinaryenStringEncodeWTF16();
    int BinaryenStringEncodeUTF8Array();
    int BinaryenStringEncodeLossyUTF8Array();
    int BinaryenStringEncodeWTF8Array();
    int BinaryenStringEncodeWTF16Array();
    int BinaryenStringAsWTF8();
    int BinaryenStringAsWTF16();
    int BinaryenStringAsIter();
    int BinaryenStringIterMoveAdvance();
    int BinaryenStringIterMoveRewind();
    int BinaryenStringSliceWTF8();
    int BinaryenStringSliceWTF16();
    int BinaryenStringEqEqual();
    int BinaryenStringEqCompare();

    // Expression
    public static class BinaryenExpression extends Structure {};
    public static class BinaryenExpressionRef extends PointerType {};
    /**
     * Block: name can be NULL. Specifying BinaryenUndefined() as the 'type'
     *      parameter indicates that the block's type shall be figured out
     *      automatically instead of explicitly providing it. This conforms
     *      to the behavior before the 'type' parameter has been introduced.
     */
    BinaryenExpressionRef BinaryenBlock(
        BinaryenModuleRef module,
        String name,
        BinaryenExpressionRef[] children,
        int numChildren,
        int type);
    BinaryenExpressionRef BinaryenIf(
        BinaryenModuleRef module,
        BinaryenExpressionRef condition,
        BinaryenExpressionRef ifTrue,
        BinaryenExpressionRef ifFalse);
    BinaryenExpressionRef BinaryenLoop(
        BinaryenModuleRef module,
        String in,
        BinaryenExpressionRef body);
    BinaryenExpressionRef BinaryenBreak(
        BinaryenModuleRef module,
        String name,
        BinaryenExpressionRef condition,
        BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenSwitch(
        BinaryenModuleRef module,
        String[] names,
        int numNames,
        String defaultName,
        BinaryenExpressionRef condition,
        BinaryenExpressionRef value);

    // Call: Note the 'returnType' parameter. You must declare the
    //       type returned by the function being called, as that
    //       function might not have been created yet, so we don't
    //       know what it is.
    BinaryenExpressionRef BinaryenCall(
        BinaryenModuleRef module,
        String target,
        BinaryenExpressionRef[] operands,
        int numOperands,
        int returnType);
    // TODO
    // BinaryenExpressionRef BinaryenCallIndirect(
    //     BinaryenModuleRef module,
    //     String table, // ?
    //     BinaryenExpressionRef target,
    //     BinaryenExpressionRef[] operands,
    //     int numOperands,
    //     int params,
    //     int results);
    BinaryenExpressionRef BinaryenReturnCall(
        BinaryenModuleRef module,
        String target,
        BinaryenExpressionRef[] operands,
        int numOperands,
        int returnType);
    // TODO
    //    BinaryenExpressionRef BinaryenReturnCallIndirect(
    //        BinaryenModuleRef module,
    //        String table,
    //        BinaryenExpressionRef target,
    //        BinaryenExpressionRef[] operands,
    //        int numOperands,
    //        int params,
    //        int results);
    BinaryenExpressionRef BinaryenLocalGet(BinaryenModuleRef module, int index, BinaryenType type);
    BinaryenExpressionRef BinaryenLocalSet(BinaryenModuleRef module, int index, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenLocalTee(BinaryenModuleRef module, int index, BinaryenExpressionRef value, BinaryenType type);
    BinaryenExpressionRef BinaryenGlobalGet(BinaryenModuleRef module, String name, int type);
    BinaryenExpressionRef BinaryenGlobalSet(BinaryenModuleRef module, String name, BinaryenExpressionRef value);
	// TODO: BinaryenLoad and BinaryenStore
    BinaryenExpressionRef BinaryenConst(BinaryenModuleRef module, BinaryenLiteral.ByValue value);
    BinaryenExpressionRef BinaryenUnary(BinaryenModuleRef module, int op, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenBinary(BinaryenModuleRef module, int op, BinaryenExpressionRef left, BinaryenExpressionRef right);
    BinaryenExpressionRef BinaryenSelect(BinaryenModuleRef module, BinaryenExpressionRef condition, BinaryenExpressionRef ifTrue, BinaryenExpressionRef ifFalse, int type);
    BinaryenExpressionRef BinaryenDrop(BinaryenModuleRef module, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenReturn(BinaryenModuleRef module, BinaryenExpressionRef value);
    // TODO: BinaryenMemorySize - BinaryenMemoryFill
    BinaryenExpressionRef BinaryenRefIsNull(BinaryenModuleRef module, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenRefAs(BinaryenModuleRef module, int op, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenRefFunc(BinaryenModuleRef module, String func, int type);
    BinaryenExpressionRef BinaryenRefEq(BinaryenModuleRef module, BinaryenExpressionRef left, BinaryenExpressionRef right);
    // TODO: BinaryenTableGet/Set/Size/Glow

    BinaryenExpressionRef BinaryenTry(BinaryenModuleRef module, String name, BinaryenExpressionRef body,
                                      String[] catchTags, int numCatchTags, BinaryenExpressionRef[] catchBodies,
                                      int numCatchBodies, String delegateTarget);
    BinaryenExpressionRef BinaryenThrow(BinaryenModuleRef module, String tag, BinaryenExpressionRef[] operands, int numOperands);
    BinaryenExpressionRef BinaryenRethrow(BinaryenModuleRef module, String target);
    // TDOO: BinaryenTupleMake, BinaryenTupleExtract, BinaryenPop
    /** Passing in null for |operands| (and 0 for |numOperands|) implies this is struct.new_default. */
    BinaryenExpressionRef BinaryenStructNew(BinaryenModuleRef module, BinaryenExpressionRef[] operands, int numOperands, int type);
    BinaryenExpressionRef BinaryenStructGet(BinaryenModuleRef module, int index, BinaryenExpressionRef ref, int type, boolean signed);
    BinaryenExpressionRef BinaryenStructSet(BinaryenModuleRef module, int index, BinaryenExpressionRef ref, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenArrayNew(BinaryenModuleRef module, int type, BinaryenExpressionRef size, BinaryenExpressionRef init);

    BinaryenExpressionRef BinaryenArrayNewFixed(BinaryenModuleRef module, int type, BinaryenExpressionRef[] values, int numValues);
    BinaryenExpressionRef BinaryenArrayGet(BinaryenModuleRef module, BinaryenExpressionRef ref, BinaryenExpressionRef index, int type, boolean signed);
    BinaryenExpressionRef BinaryenArraySet(BinaryenModuleRef module, BinaryenExpressionRef ref, BinaryenExpressionRef index, BinaryenExpressionRef value);
    BinaryenExpressionRef BinaryenArrayLen(BinaryenModuleRef module, BinaryenExpressionRef ref);
    BinaryenExpressionRef BinaryenArrayCopy(BinaryenModuleRef module, BinaryenExpressionRef destRef, BinaryenExpressionRef destIndex, BinaryenExpressionRef srcRef, BinaryenExpressionRef srcIndex, BinaryenExpressionRef length);
    // TODO: BinaryenString*

    // Expression
    // TODO: BinaryenExpressionGetId - BinaryenStringSliceIterGetNum

    // Functions
    public static class BinaryenFunction extends Structure {};
    public static class BinaryenFunctionRef extends PointerType {};
    /** Adds a function to the module. This is thread-safe.
     * @param varTypes the types of variables. In WebAssembly, vars share
     *     an index space with params. In other words, params come from
     *     the function type, and vars are provided in this call, and
     *     together they are all the locals. The order is first params
     *     and then vars, so if you have one param it will be at index
     *     0 (and written $0), and if you also have 2 vars they will be
     *     at indexes 1 and 2, etc., that is, they share an index space.
     */
    BinaryenFunctionRef BinaryenAddFunction(BinaryenModuleRef module, String name, BinaryenType params, BinaryenType results,
                                                        int[] varTypes, int numVarTypes, BinaryenExpressionRef body);
    /** As BinaryenAddFunction, but takes a HeapType rather than params and results. This lets you set the specific type of the function. */
    BinaryenFunctionRef BinaryenAddFunctionWithHeapType(BinaryenModuleRef module, String name, int type,
                                                        int[] varTypes, int numVarTypes, BinaryenExpressionRef body);
    /** Gets a function reference by name. Returns NULL if the function does not exist. */
    BinaryenFunctionRef BinaryenGetFunction(BinaryenModuleRef module, String name);
    /** Removes a function by name */
    void BinaryenRemoveFunction(BinaryenModuleRef module, String name);
    int BinaryenGetNumFunctions(BinaryenModuleRef module);
    BinaryenFunctionRef BinaryenGetFunctionByIndex(BinaryenModuleRef module, int index);

    void BinaryenAddFunctionImport(BinaryenModuleRef module, String internalName, String externalModuleName, String externalBaseName, int params, int results);
    void BinaryenAddTableImport(BinaryenModuleRef module, String internalName, String externalModuleName, String externalBaseName);
    void BinaryenAddMemoryImport(BinaryenModuleRef module, String internalName, String externalModuleName, String externalBaseName, byte shared);
    void BinaryenAddGlobalImport(BinaryenModuleRef module, String internalName, String externalModuleName, String externalBaseName, int globalType, boolean mutable_);
    void BinaryenAddTagImport(BinaryenModuleRef module, String internalName, String externalModuleName, String externalBaseName, int params, int results);

    public static class BinaryenMemory extends Structure {};
    public static class BinaryenMemoryRef extends PointerType {};

    public static class BinaryenExport extends Structure {};
    public static class BinaryenExportRef extends PointerType {};
    BinaryenExportRef BinaryenAddFunctionExport(BinaryenModuleRef module, String internalName, String externalName);
    BinaryenExportRef BinaryenAddTableExport(BinaryenModuleRef module, String internalName, String externalName);
    BinaryenExportRef BinaryenAddMemoryExport(BinaryenModuleRef module, String internalName, String externalName);
    BinaryenExportRef BinaryenAddGlobalExport(BinaryenModuleRef module, String internalName, String externalName);
    BinaryenExportRef BinaryenAddTagExport(BinaryenModuleRef module, String internalName, String externalName);
    BinaryenExportRef BinaryenGetExport(BinaryenModuleRef module, String externalName);
    void BinaryenRemoveExport(BinaryenModuleRef module, String externalName);
    int BinaryenGetNumExports(BinaryenModuleRef module);
    BinaryenExportRef BinaryenGetExportByIndex(BinaryenModuleRef module, int index);

    public static class BinaryenGlobal extends Structure {};
    public static class BinaryenGlobalRef extends PointerType {};
    BinaryenGlobalRef BinaryenAddGlobal(BinaryenModuleRef module,
                                         String name,
                                         int type,
                                         boolean mutable,
                                         BinaryenExpressionRef init);
    BinaryenGlobalRef BinaryenGetGlobal(BinaryenModuleRef module, String name);
    void BinaryenRemoveGlobal(BinaryenModuleRef module, String name);
    int BinaryenGetNumGlobals(BinaryenModuleRef module);
    BinaryenGlobalRef BinaryenGetGlobalByIndex(BinaryenModuleRef module, int index);

    public static class BinaryenTag extends Structure {};
    public static class BinaryenTagRef extends PointerType {};
    BinaryenTagRef BinaryenAddTag(BinaryenModuleRef module, String name, int params, int results);
    BinaryenTagRef BinaryenGetTag(BinaryenModuleRef module, String name);
    void BinaryenRemoveTag(BinaryenModuleRef module, String name);

    // TODO: BINARYEN_REF(Table);
    // TODO: BINARYEN_REF(ElementSegment);
    // TODO: BinaryenGetNumElementSegments - BinaryenSetStart

    // TODO: BinaryenModuleGetFeatures - BinaryenModuleSetFeatures

    BinaryenModuleRef BinaryenModuleParse(String text);
    void BinaryenModulePrint(BinaryenModuleRef module);
    void BinaryenModulePrintStackIR(BinaryenModuleRef module, boolean optimize);
    void BinaryenModulePrintAsmjs(BinaryenModuleRef module);
    boolean BinaryenModuleValidate(BinaryenModuleRef module);
    void BinaryenModuleOptimize(BinaryenModuleRef module);
    void BinaryenModuleUpdateMaps(BinaryenModuleRef module);
    int BinaryenGetOptimizeLevel();
    void BinaryenSetOptimizeLevel(int level);
    int BinaryenGetShrinkLevel();
    void BinaryenSetShrinkLevel(int level);
    boolean BinaryenGetDebugInfo();
    void BinaryenSetDebugInfo(boolean on);
    boolean BinaryenGetLowMemoryUnused();
    void BinaryenSetLowMemoryUnused(boolean on);
    boolean BinaryenGetZeroFilledMemory();
    void BinaryenSetZeroFilledMemory(boolean on);
    boolean BinaryenGetFastMath();
    void BinaryenSetFastMath(boolean value);
    Pointer BinaryenGetPassArgument(String name);
    void BinaryenSetPassArgument(String name, String value);
    void BinaryenClearPassArguments();
    int BinaryenGetAlwaysInlineMaxSize();
    void BinaryenSetAlwaysInlineMaxSize(int size);
    int BinaryenGetFlexibleInlineMaxSize();
    void BinaryenSetFlexibleInlineMaxSize(int size);
    int BinaryenGetOneCallerInlineMaxSize();
    void BinaryenSetOneCallerInlineMaxSize(int size);
    boolean BinaryenGetAllowInliningFunctionsWithLoops();
    void BinaryenSetAllowInliningFunctionsWithLoops(boolean enabled);
    void BinaryenModuleRunPasses(BinaryenModuleRef module, String[] passes, int numPasses);
    void BinaryenModuleAutoDrop(BinaryenModuleRef module);
    int BinaryenModuleWrite(BinaryenModuleRef module, char[] output, int outputSize);
    int BinaryenModuleWriteText(BinaryenModuleRef module, char[] output, int outputSize);
    int BinaryenModuleWriteStackIR(BinaryenModuleRef module, char[] output, int outputSize, boolean optimize);

    // TODO: BinaryenModuleWriteWithSourceMap
    // TODO: BinaryenModuleAllocateAndWrite
    // TODO: BinaryenModuleAllocateAndWriteStackIR
    // TODO: BinaryenModuleRef BinaryenModuleRead(String input, int inputSize);
    void BinaryenModuleInterpret(BinaryenModuleRef module);
    int BinaryenModuleAddDebugInfoFileName(BinaryenModuleRef module, String filename);
    String BinaryenModuleGetDebugInfoFileName(BinaryenModuleRef module, int index);

    // TODO: Function Operations
    //
    // ========== Function Operations ==========
    //

    // TODO: Table Operations
    //
    // ========== Table Operations ==========
    //

    // TODO: Elem Segment Operations
    //
    // ========== Elem Segment Operations ==========
    //
    // TODO: ========== Global Operations ==========
    // TODO: Tag Operations
    // TODO: Import Operations
    // TODO: Export Operations
    // TODO: Custom Sections

    // TODO: Effect analyzers
    // TODO: CFG / Relooper

    // TODO: ExpressionRunner


    public static class TypeBuilderRef extends PointerType {};
    /** Indicates a cycle in the supertype relation. */
    int TypeBuilderErrorReasonSelfSupertype();
    /** Indicates that the declared supertype of a type is invalid. */
    int TypeBuilderErrorReasonInvalidSupertype();
    /** Indicates that the declared supertype is an invalid forward reference. */
    int TypeBuilderErrorReasonForwardSupertypeReference();
    /** Indicates that a child of a type is an invalid forward reference. */
    int TypeBuilderErrorReasonForwardChildReference();

    TypeBuilderRef TypeBuilderCreate(int size);
    void TypeBuilderGrow(TypeBuilderRef builder, int count);
    int TypeBuilderGetSize(TypeBuilderRef builder);

    void TypeBuilderSetSignatureType(TypeBuilderRef builder, int index, int paramTypes, int resultTypes);
    void TypeBuilderSetStructType(TypeBuilderRef builder, int index, BinaryenType[] fieldTypes, BinaryenPackedType[] fieldPackedTypes, boolean[] fieldMutables, int numFields);
    void TypeBuilderSetArrayType(TypeBuilderRef builder, int index, int elementType, int elementPackedType, int elementMutable);
    int TypeBuilderGetTempHeapType(TypeBuilderRef builder, int index);
    int TypeBuilderGetTempTupleType(TypeBuilderRef builder, int[] types, int numTypes);
    int TypeBuilderGetTempRefType(TypeBuilderRef builder, BinaryenHeapType heapType, int nullable);
    void TypeBuilderSetSubType(TypeBuilderRef builder, int index, int superType);
    void TypeBuilderSetOpen(TypeBuilderRef builder, int index);
    void TypeBuilderCreateRecGroup(TypeBuilderRef builder, int index, int length);
    boolean TypeBuilderBuildAndDispose(TypeBuilderRef builder, BinaryenHeapType[] heapTypes, IntByReference errorIndex, IntByReference errorReason);

    void BinaryenModuleSetTypeName(BinaryenModuleRef module, BinaryenHeapType heapType, String name);
    void BinaryenModuleSetFieldName(BinaryenModuleRef module, BinaryenHeapType heapType, int index, String name);

    void BinaryenSetColorsEnabled(boolean enabled);
    boolean BinaryenAreColorsEnabled();
}
