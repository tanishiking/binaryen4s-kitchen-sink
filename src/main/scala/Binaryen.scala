import com.sun.jna._

trait Binaryen extends Library {

  class BinaryenModule extends Structure
  class BinaryenExpression extends Structure
  class BinaryenFunction extends Structure
  class BinaryenMemory extends Structure
  class BinaryenExport extends Structure
  class BinaryenGlobal extends Structure
  class BinaryenTag extends Structure
  class BinaryenTable extends Structure
  class BinaryenElementSegment extends Structure
  class Relooper extends Structure
  class RelooperBlock extends Structure
  class CExpressionRunner extends Structure
  class TypeBuilder extends Structure
  class BinaryenLiteral extends Structure
  class BinaryenBufferSizes extends Structure
  class BinaryenModuleAllocateAndWriteResult extends Structure

  type BinaryenIndex = Int
  type BinaryenType = Long
  type BinaryenPackedType = Int
  type BinaryenHeapType = Pointer
  type BinaryenExpressionId = Int
  type BinaryenExternalKind = Int
  type BinaryenFeatures = Int
  type BinaryenModuleRef = Pointer
  type BinaryenOp = Int
  type BinaryenExpressionRef = Pointer
  type BinaryenFunctionRef = Pointer
  type BinaryenMemoryRef = Pointer
  type BinaryenExportRef = Pointer
  type BinaryenGlobalRef = Pointer
  type BinaryenTagRef = Pointer
  type BinaryenTableRef = Pointer
  type BinaryenElementSegmentRef = Pointer
  type RelooperRef = Pointer
  type RelooperBlockRef = Pointer
  type ExpressionRunnerRef = Pointer
  type TypeBuilderRef = Pointer
  type TypeBuilderErrorReason = Int
  type BinaryenBasicHeapType = Int
  type BinaryenSideEffects = Int
  type ExpressionRunnerFlags = Int

  def BinaryenTypeNone(): BinaryenType
  def BinaryenTypeInt32(): BinaryenType
  def BinaryenTypeInt64(): BinaryenType
  def BinaryenTypeFloat32(): BinaryenType
  def BinaryenTypeFloat64(): BinaryenType
  def BinaryenTypeVec128(): BinaryenType
  def BinaryenTypeFuncref(): BinaryenType
  def BinaryenTypeExternref(): BinaryenType
  def BinaryenTypeAnyref(): BinaryenType
  def BinaryenTypeEqref(): BinaryenType
  def BinaryenTypeI31ref(): BinaryenType
  def BinaryenTypeStructref(): BinaryenType
  def BinaryenTypeArrayref(): BinaryenType
  def BinaryenTypeStringref(): BinaryenType
  def BinaryenTypeStringviewWTF8(): BinaryenType
  def BinaryenTypeStringviewWTF16(): BinaryenType
  def BinaryenTypeStringviewIter(): BinaryenType
  def BinaryenTypeNullref(): BinaryenType
  def BinaryenTypeNullExternref(): BinaryenType
  def BinaryenTypeNullFuncref(): BinaryenType
  def BinaryenTypeUnreachable(): BinaryenType

  /** Not a real type. Used as the last parameter to BinaryenBlock to let the API figure out the type instead of
    * providing one.
    */
  def BinaryenTypeAuto(): BinaryenType

  /** Creates a BinaryenType with the provided value types. */
  def BinaryenTypeCreate(valueTypes: Array[BinaryenType], numTypes: BinaryenIndex): BinaryenType

  /** Gets the arity of the BinaryenType. */
  def BinaryenTypeArity(t: BinaryenType): Int // uint32_t

  /** Expands the BinaryenType into a buffer. */
  def BinaryenTypeExpand(t: BinaryenType, buf: Array[BinaryenType]): Unit

  // Packed types (call to get the value of each; you can cache them)
  def BinaryenPackedTypeNotPacked(): BinaryenPackedType
  def BinaryenPackedTypeInt8(): BinaryenPackedType
  def BinaryenPackedTypeInt16(): BinaryenPackedType

  def BinaryenHeapTypeExt(): BinaryenHeapType
  def BinaryenHeapTypeFunc(): BinaryenHeapType
  def BinaryenHeapTypeAny(): BinaryenHeapType
  def BinaryenHeapTypeEq(): BinaryenHeapType
  def BinaryenHeapTypeI31(): BinaryenHeapType
  def BinaryenHeapTypeStruct(): BinaryenHeapType
  def BinaryenHeapTypeArray(): BinaryenHeapType
  def BinaryenHeapTypeString(): BinaryenHeapType
  def BinaryenHeapTypeStringviewWTF8(): BinaryenHeapType
  def BinaryenHeapTypeStringviewWTF16(): BinaryenHeapType
  def BinaryenHeapTypeStringviewIter(): BinaryenHeapType
  def BinaryenHeapTypeNone(): BinaryenHeapType
  def BinaryenHeapTypeNoext(): BinaryenHeapType
  def BinaryenHeapTypeNofunc(): BinaryenHeapType

  def BinaryenHeapTypeIsBasic(heapType: BinaryenHeapType): Boolean
  def BinaryenHeapTypeIsSignature(heapType: BinaryenHeapType): Boolean
  def BinaryenHeapTypeIsStruct(heapType: BinaryenHeapType): Boolean
  def BinaryenHeapTypeIsArray(heapType: BinaryenHeapType): Boolean
  def BinaryenHeapTypeIsBottom(heapType: BinaryenHeapType): Boolean

  // BinaryenHeapType functions
  def BinaryenHeapTypeGetBottom(heapType: BinaryenHeapType): BinaryenHeapType
  def BinaryenHeapTypeIsSubType(
      left: BinaryenHeapType,
      right: BinaryenHeapType
  ): Boolean
  def BinaryenStructTypeGetNumFields(heapType: BinaryenHeapType): BinaryenIndex
  def BinaryenStructTypeGetFieldType(
      heapType: BinaryenHeapType,
      index: BinaryenIndex
  ): BinaryenType
  def BinaryenStructTypeGetFieldPackedType(
      heapType: BinaryenHeapType,
      index: BinaryenIndex
  ): BinaryenPackedType
  def BinaryenStructTypeIsFieldMutable(
      heapType: BinaryenHeapType,
      index: BinaryenIndex
  ): Boolean
  def BinaryenArrayTypeGetElementType(heapType: BinaryenHeapType): BinaryenType
  def BinaryenArrayTypeGetElementPackedType(
      heapType: BinaryenHeapType
  ): BinaryenPackedType
  def BinaryenArrayTypeIsElementMutable(heapType: BinaryenHeapType): Boolean
  def BinaryenSignatureTypeGetParams(heapType: BinaryenHeapType): BinaryenType
  def BinaryenSignatureTypeGetResults(heapType: BinaryenHeapType): BinaryenType

  // BinaryenType functions
  def BinaryenTypeGetHeapType(`type`: BinaryenType): BinaryenHeapType
  def BinaryenTypeIsNullable(`type`: BinaryenType): Boolean
  def BinaryenTypeFromHeapType(
      heapType: BinaryenHeapType,
      nullable: Boolean
  ): BinaryenType

  // TODO: Expression ids (call to get the value of each; you can cache them)
  // TODO: External kinds
  // TODO: BinaryenFeatures

  // Modules
  def BinaryenModuleCreate(): BinaryenModuleRef
  def BinaryenModuleDispose(module: BinaryenModuleRef): Unit

  // Literals. These are passes by value.
  def BinaryenLiteralInt32(x: Integer): BinaryenLiteral
  def BinaryenLiteralInt64(x: Long): BinaryenLiteral
  def BinaryenLiteralFloat32(x: Float): BinaryenLiteral
  def BinaryenLiteralFloat64(x: Double): BinaryenLiteral
  def BinaryenLiteralVec128(x: Array[Byte]): BinaryenLiteral
  def BinaryenLiteralFloat32Bits(x: Int): BinaryenLiteral
  def BinaryenLiteralFloat64Bits(x: Long): BinaryenLiteral

  // Expressions
  def BinaryenClzInt32(): BinaryenOp
  def BinaryenCtzInt32(): BinaryenOp
  def BinaryenPopcntInt32(): BinaryenOp
  def BinaryenNegFloat32(): BinaryenOp
  def BinaryenAbsFloat32(): BinaryenOp
  def BinaryenCeilFloat32(): BinaryenOp
  def BinaryenFloorFloat32(): BinaryenOp
  def BinaryenTruncFloat32(): BinaryenOp
  def BinaryenNearestFloat32(): BinaryenOp
  def BinaryenSqrtFloat32(): BinaryenOp
  def BinaryenEqZInt32(): BinaryenOp
  def BinaryenClzInt64(): BinaryenOp
  def BinaryenCtzInt64(): BinaryenOp
  def BinaryenPopcntInt64(): BinaryenOp
  def BinaryenNegFloat64(): BinaryenOp
  def BinaryenAbsFloat64(): BinaryenOp
  def BinaryenCeilFloat64(): BinaryenOp
  def BinaryenFloorFloat64(): BinaryenOp
  def BinaryenTruncFloat64(): BinaryenOp
  def BinaryenNearestFloat64(): BinaryenOp
  def BinaryenSqrtFloat64(): BinaryenOp
  def BinaryenEqZInt64(): BinaryenOp
  def BinaryenExtendSInt32(): BinaryenOp
  def BinaryenExtendUInt32(): BinaryenOp
  def BinaryenWrapInt64(): BinaryenOp
  // Int32 operations
  def BinaryenTruncSFloat32ToInt32(): BinaryenOp
  def BinaryenTruncSFloat32ToInt64(): BinaryenOp
  def BinaryenTruncUFloat32ToInt32(): BinaryenOp
  def BinaryenTruncUFloat32ToInt64(): BinaryenOp
  def BinaryenTruncSFloat64ToInt32(): BinaryenOp
  def BinaryenTruncSFloat64ToInt64(): BinaryenOp
  def BinaryenTruncUFloat64ToInt32(): BinaryenOp
  def BinaryenTruncUFloat64ToInt64(): BinaryenOp
  def BinaryenReinterpretFloat32(): BinaryenOp
  def BinaryenReinterpretFloat64(): BinaryenOp
  def BinaryenConvertSInt32ToFloat32(): BinaryenOp
  def BinaryenConvertSInt32ToFloat64(): BinaryenOp
  def BinaryenConvertUInt32ToFloat32(): BinaryenOp
  def BinaryenConvertUInt32ToFloat64(): BinaryenOp
  def BinaryenConvertSInt64ToFloat32(): BinaryenOp
  def BinaryenConvertSInt64ToFloat64(): BinaryenOp
  def BinaryenConvertUInt64ToFloat32(): BinaryenOp
  def BinaryenConvertUInt64ToFloat64(): BinaryenOp
  def BinaryenPromoteFloat32(): BinaryenOp
  def BinaryenDemoteFloat64(): BinaryenOp
  def BinaryenReinterpretInt32(): BinaryenOp
  def BinaryenReinterpretInt64(): BinaryenOp
  def BinaryenExtendS8Int32(): BinaryenOp
  def BinaryenExtendS16Int32(): BinaryenOp
  def BinaryenExtendS8Int64(): BinaryenOp
  def BinaryenExtendS16Int64(): BinaryenOp
  def BinaryenExtendS32Int64(): BinaryenOp
  def BinaryenAddInt32(): BinaryenOp
  def BinaryenSubInt32(): BinaryenOp
  def BinaryenMulInt32(): BinaryenOp
  def BinaryenDivSInt32(): BinaryenOp
  def BinaryenDivUInt32(): BinaryenOp
  def BinaryenRemSInt32(): BinaryenOp
  def BinaryenRemUInt32(): BinaryenOp
  def BinaryenAndInt32(): BinaryenOp
  def BinaryenOrInt32(): BinaryenOp
  def BinaryenXorInt32(): BinaryenOp
  def BinaryenShlInt32(): BinaryenOp
  def BinaryenShrUInt32(): BinaryenOp
  def BinaryenShrSInt32(): BinaryenOp
  def BinaryenRotLInt32(): BinaryenOp
  def BinaryenRotRInt32(): BinaryenOp
  def BinaryenEqInt32(): BinaryenOp
  def BinaryenNeInt32(): BinaryenOp
  def BinaryenLtSInt32(): BinaryenOp
  def BinaryenLtUInt32(): BinaryenOp
  def BinaryenLeSInt32(): BinaryenOp
  def BinaryenLeUInt32(): BinaryenOp
  def BinaryenGtSInt32(): BinaryenOp
  def BinaryenGtUInt32(): BinaryenOp
  def BinaryenGeSInt32(): BinaryenOp
  def BinaryenGeUInt32(): BinaryenOp

  // Int64 operations
  def BinaryenAddInt64(): BinaryenOp
  def BinaryenSubInt64(): BinaryenOp
  def BinaryenMulInt64(): BinaryenOp
  def BinaryenDivSInt64(): BinaryenOp
  def BinaryenDivUInt64(): BinaryenOp
  def BinaryenRemSInt64(): BinaryenOp
  def BinaryenRemUInt64(): BinaryenOp
  def BinaryenAndInt64(): BinaryenOp
  def BinaryenOrInt64(): BinaryenOp
  def BinaryenXorInt64(): BinaryenOp
  def BinaryenShlInt64(): BinaryenOp
  def BinaryenShrUInt64(): BinaryenOp
  def BinaryenShrSInt64(): BinaryenOp
  def BinaryenRotLInt64(): BinaryenOp
  def BinaryenRotRInt64(): BinaryenOp
  def BinaryenEqInt64(): BinaryenOp
  def BinaryenNeInt64(): BinaryenOp
  def BinaryenLtSInt64(): BinaryenOp
  def BinaryenLtUInt64(): BinaryenOp
  def BinaryenLeSInt64(): BinaryenOp
  def BinaryenLeUInt64(): BinaryenOp
  def BinaryenGtSInt64(): BinaryenOp
  def BinaryenGtUInt64(): BinaryenOp
  def BinaryenGeSInt64(): BinaryenOp
  def BinaryenGeUInt64(): BinaryenOp

  // Float32 operations
  def BinaryenAddFloat32(): BinaryenOp
  def BinaryenSubFloat32(): BinaryenOp
  def BinaryenMulFloat32(): BinaryenOp
  def BinaryenDivFloat32(): BinaryenOp
  def BinaryenCopySignFloat32(): BinaryenOp
  def BinaryenMinFloat32(): BinaryenOp
  def BinaryenMaxFloat32(): BinaryenOp
  def BinaryenEqFloat32(): BinaryenOp
  def BinaryenNeFloat32(): BinaryenOp
  def BinaryenLtFloat32(): BinaryenOp
  def BinaryenLeFloat32(): BinaryenOp
  def BinaryenGtFloat32(): BinaryenOp
  def BinaryenGeFloat32(): BinaryenOp

  // Float64 operations
  def BinaryenAddFloat64(): BinaryenOp
  def BinaryenSubFloat64(): BinaryenOp
  def BinaryenMulFloat64(): BinaryenOp
  def BinaryenDivFloat64(): BinaryenOp
  def BinaryenCopySignFloat64(): BinaryenOp
  def BinaryenMinFloat64(): BinaryenOp
  def BinaryenMaxFloat64(): BinaryenOp
  def BinaryenEqFloat64(): BinaryenOp
  def BinaryenNeFloat64(): BinaryenOp
  def BinaryenLtFloat64(): BinaryenOp
  def BinaryenLeFloat64(): BinaryenOp
  def BinaryenGtFloat64(): BinaryenOp
  def BinaryenGeFloat64(): BinaryenOp
  // TODO: rest of BinaryenOperations from BinaryenAtomicRMWAdd to BinaryenDotI8x16I7x16SToVecI16x8
  // Ref operations
  def BinaryenRefAsNonNull(): BinaryenOp
  def BinaryenRefAsExternInternalize(): BinaryenOp
  def BinaryenRefAsExternExternalize(): BinaryenOp
  def BinaryenBrOnNull(): BinaryenOp
  def BinaryenBrOnNonNull(): BinaryenOp
  def BinaryenBrOnCast(): BinaryenOp
  def BinaryenBrOnCastFail(): BinaryenOp
  // TODO: BinaryenStringNewUTF8 to BinaryenStringEqCompare

  /** Block: name can be NULL. Specifying BinaryenUndefined() as the 'type' parameter indicates that the block's type
    * shall be figured out automatically instead of explicitly providing it. This conforms to the behavior before the
    * 'type' parameter has been introduced.
    */
  def BinaryenBlock(
      module: BinaryenModuleRef,
      name: String,
      children: Array[BinaryenExpressionRef],
      numChildren: BinaryenIndex,
      `type`: BinaryenType
  ): BinaryenExpressionRef

  /** If: ifFalse can be NULL */
  def BinaryenIf(
      module: BinaryenModuleRef,
      condition: BinaryenExpressionRef,
      ifTrue: BinaryenExpressionRef,
      ifFalse: BinaryenExpressionRef
  ): BinaryenExpressionRef

  def BinaryenLoop(
      module: BinaryenModuleRef,
      in: String,
      body: BinaryenExpressionRef
  ): BinaryenExpressionRef

  /** Break: value and condition can be NULL */
  def BinaryenBreak(
      module: BinaryenModuleRef,
      name: String,
      condition: BinaryenExpressionRef,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef

  def BinaryenSwitch(
      module: BinaryenModuleRef,
      names: Array[String],
      numNames: BinaryenIndex,
      defaultName: String,
      condition: BinaryenExpressionRef,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef

  def BinaryenCall(
      module: BinaryenModuleRef,
      target: String,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      returnType: BinaryenType
  ): BinaryenExpressionRef

  def BinaryenCallIndirect(
      module: BinaryenModuleRef,
      table: String,
      target: BinaryenExpressionRef,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      params: BinaryenType,
      results: BinaryenType
  ): BinaryenExpressionRef

  def BinaryenReturnCall(
      module: BinaryenModuleRef,
      target: String,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      returnType: BinaryenType
  ): BinaryenExpressionRef

  def BinaryenReturnCallIndirect(
      module: BinaryenModuleRef,
      table: String,
      target: BinaryenExpressionRef,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      params: BinaryenType,
      results: BinaryenType
  ): BinaryenExpressionRef

  /** Note the 'type' parameter. It might seem redundant, since the local at that index must have a type. However, this
    * API lets you build code "top-down": create a node, then its parents, and so on, and finally create the function at
    * the end. (Note that in fact you do not mention a function when creating ExpressionRefs, only a module.) And since
    * LocalGet is a leaf node, we need to be told its type. (Other nodes detect their type either from their type or
    * their opcode, or failing that, their children. But LocalGet has no children, it is where a "stream" of type info
    * begins.)
    *
    * Note also that the index of a local can refer to a param or a var, that is, either a parameter to the function or
    * a variable declared when you call BinaryenAddFunction. See BinaryenAddFunction for more details.
    */
  def BinaryenLocalGet(module: BinaryenModuleRef, index: BinaryenIndex, `type`: BinaryenType): BinaryenExpressionRef
  def BinaryenLocalSet(
      module: BinaryenModuleRef,
      index: BinaryenIndex,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenLocalTee(
      module: BinaryenModuleRef,
      index: BinaryenIndex,
      value: BinaryenExpressionRef,
      `type`: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenGlobalGet(module: BinaryenModuleRef, name: String, `type`: BinaryenType): BinaryenExpressionRef
  def BinaryenGlobalSet(module: BinaryenModuleRef, name: String, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenLoad(
      module: BinaryenModuleRef,
      bytes: Int,
      signed: Boolean,
      offset: Int,
      align: Int,
      `type`: BinaryenType,
      ptr: BinaryenExpressionRef,
      memoryName: String
  ): BinaryenExpressionRef
  def BinaryenStore(
      module: BinaryenModuleRef,
      bytes: Int,
      offset: Int,
      align: Int,
      ptr: BinaryenExpressionRef,
      value: BinaryenExpressionRef,
      `type`: BinaryenType,
      memoryName: String
  ): BinaryenExpressionRef
  def BinaryenConst(module: BinaryenModuleRef, value: BinaryenLiteral): BinaryenExpressionRef
  def BinaryenUnary(module: BinaryenModuleRef, op: BinaryenOp, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenBinary(
      module: BinaryenModuleRef,
      op: BinaryenOp,
      left: BinaryenExpressionRef,
      right: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenSelect(
      module: BinaryenModuleRef,
      condition: BinaryenExpressionRef,
      ifTrue: BinaryenExpressionRef,
      ifFalse: BinaryenExpressionRef,
      `type`: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenDrop(module: BinaryenModuleRef, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenReturn(module: BinaryenModuleRef, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenMemorySize(module: BinaryenModuleRef, memoryName: String, memoryIs64: Boolean): BinaryenExpressionRef
  def BinaryenMemoryGrow(
      module: BinaryenModuleRef,
      delta: BinaryenExpressionRef,
      memoryName: String,
      memoryIs64: Boolean
  ): BinaryenExpressionRef
  def BinaryenNop(module: BinaryenModuleRef): BinaryenExpressionRef
  def BinaryenUnreachable(module: BinaryenModuleRef): BinaryenExpressionRef

  // TODO: BinaryenAtomicLoad - BinaryenMemoryFill

  def BinaryenRefNull(module: BinaryenModuleRef, `type`: BinaryenType): BinaryenExpressionRef
  def BinaryenRefIsNull(module: BinaryenModuleRef, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenRefAs(module: BinaryenModuleRef, op: BinaryenOp, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenRefFunc(module: BinaryenModuleRef, func: String, `type`: BinaryenType): BinaryenExpressionRef
  def BinaryenRefEq(
      module: BinaryenModuleRef,
      left: BinaryenExpressionRef,
      right: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenTableGet(
      module: BinaryenModuleRef,
      name: String,
      index: BinaryenExpressionRef,
      `type`: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenTableSet(
      module: BinaryenModuleRef,
      name: String,
      index: BinaryenExpressionRef,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenTableSize(module: BinaryenModuleRef, name: String): BinaryenExpressionRef
  def BinaryenTableGrow(
      module: BinaryenModuleRef,
      name: String,
      value: BinaryenExpressionRef,
      delta: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenTry(
      module: BinaryenModuleRef,
      name: String,
      body: BinaryenExpressionRef,
      catchTags: Array[String],
      numCatchTags: BinaryenIndex,
      catchBodies: Array[BinaryenExpressionRef],
      numCatchBodies: BinaryenIndex,
      delegateTarget: String
  ): BinaryenExpressionRef
  def BinaryenThrow(
      module: BinaryenModuleRef,
      tag: String,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex
  ): BinaryenExpressionRef
  def BinaryenRethrow(module: BinaryenModuleRef, target: String): BinaryenExpressionRef
  def BinaryenTupleMake(
      module: BinaryenModuleRef,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex
  ): BinaryenExpressionRef
  def BinaryenTupleExtract(
      module: BinaryenModuleRef,
      tuple: BinaryenExpressionRef,
      index: BinaryenIndex
  ): BinaryenExpressionRef
  def BinaryenPop(module: BinaryenModuleRef, `type`: BinaryenType): BinaryenExpressionRef
  def BinaryenRefI31(module: BinaryenModuleRef, value: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenI31Get(module: BinaryenModuleRef, i31: BinaryenExpressionRef, signed: Boolean): BinaryenExpressionRef
  def BinaryenCallRef(
      module: BinaryenModuleRef,
      target: BinaryenExpressionRef,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      `type`: BinaryenType,
      isReturn: Boolean
  ): BinaryenExpressionRef
  def BinaryenRefTest(
      module: BinaryenModuleRef,
      ref: BinaryenExpressionRef,
      castType: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenRefCast(
      module: BinaryenModuleRef,
      ref: BinaryenExpressionRef,
      `type`: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenBrOn(
      module: BinaryenModuleRef,
      op: BinaryenOp,
      name: String,
      ref: BinaryenExpressionRef,
      castType: BinaryenType
  ): BinaryenExpressionRef
  def BinaryenStructNew(
      module: BinaryenModuleRef,
      operands: Array[BinaryenExpressionRef],
      numOperands: BinaryenIndex,
      `type`: BinaryenHeapType
  ): BinaryenExpressionRef
  def BinaryenStructGet(
      module: BinaryenModuleRef,
      index: BinaryenIndex,
      ref: BinaryenExpressionRef,
      `type`: BinaryenType,
      signed: Boolean
  ): BinaryenExpressionRef
  def BinaryenStructSet(
      module: BinaryenModuleRef,
      index: BinaryenIndex,
      ref: BinaryenExpressionRef,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenArrayNew(
      module: BinaryenModuleRef,
      `type`: BinaryenHeapType,
      size: BinaryenExpressionRef,
      init: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenArrayNewFixed(
      module: BinaryenModuleRef,
      `type`: BinaryenHeapType,
      values: Array[BinaryenExpressionRef],
      numValues: BinaryenIndex
  ): BinaryenExpressionRef
  def BinaryenArrayGet(
      module: BinaryenModuleRef,
      ref: BinaryenExpressionRef,
      index: BinaryenExpressionRef,
      `type`: BinaryenType,
      signed: Boolean
  ): BinaryenExpressionRef
  def BinaryenArraySet(
      module: BinaryenModuleRef,
      ref: BinaryenExpressionRef,
      index: BinaryenExpressionRef,
      value: BinaryenExpressionRef
  ): BinaryenExpressionRef
  def BinaryenArrayLen(module: BinaryenModuleRef, ref: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenArrayCopy(
      module: BinaryenModuleRef,
      destRef: BinaryenExpressionRef,
      destIndex: BinaryenExpressionRef,
      srcRef: BinaryenExpressionRef,
      srcIndex: BinaryenExpressionRef,
      length: BinaryenExpressionRef
  ): BinaryenExpressionRef

  // TODO: BinaryenStringNew - BinaryenStringSliceIter

  // Expression
  /** Gets the id (kind) of the given expression. */
  def BinaryenExpressionGetId(expr: BinaryenExpressionRef): BinaryenExpressionId

  /** Gets the type of the given expression. */
  def BinaryenExpressionGetType(expr: BinaryenExpressionRef): BinaryenType

  /** Sets the type of the given expression. */
  def BinaryenExpressionSetType(
      expr: BinaryenExpressionRef,
      `type`: BinaryenType
  ): Unit

  /** Prints text format of the given expression to stdout. */
  def BinaryenExpressionPrint(expr: BinaryenExpressionRef): Unit

  /** Re-finalizes an expression after it has been modified. */
  def BinaryenExpressionFinalize(expr: BinaryenExpressionRef): Unit

  /** Makes a deep copy of the given expression. */
  def BinaryenExpressionCopy(
      expr: BinaryenExpressionRef,
      module: BinaryenModuleRef
  ): BinaryenExpressionRef

  // Block
  def BinaryenBlockGetName(expr: Pointer): String
  def BinaryenBlockSetName(expr: Pointer, name: String): Unit
  def BinaryenBlockGetNumChildren(expr: Pointer): Int
  def BinaryenBlockGetChildAt(expr: Pointer, index: Int): Pointer
  def BinaryenBlockSetChildAt(expr: Pointer, index: Int, childExpr: Pointer): Unit
  def BinaryenBlockAppendChild(expr: Pointer, childExpr: Pointer): Int
  def BinaryenBlockInsertChildAt(expr: Pointer, index: Int, childExpr: Pointer): Unit
  def BinaryenBlockRemoveChildAt(expr: Pointer, index: Int): Pointer

  // If
  def BinaryenIfGetCondition(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenIfSetCondition(expr: BinaryenExpressionRef, condExpr: BinaryenExpressionRef): Unit
  def BinaryenIfGetIfTrue(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenIfSetIfTrue(expr: BinaryenExpressionRef, ifTrueExpr: BinaryenExpressionRef): Unit
  def BinaryenIfGetIfFalse(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenIfSetIfFalse(expr: BinaryenExpressionRef, ifFalseExpr: BinaryenExpressionRef): Unit

  // Loop
  def BinaryenLoopGetName(expr: BinaryenExpressionRef): String
  def BinaryenLoopSetName(expr: BinaryenExpressionRef, name: String): Unit
  def BinaryenLoopGetBody(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenLoopSetBody(expr: BinaryenExpressionRef, bodyExpr: BinaryenExpressionRef): Unit

// Break
  def BinaryenBreakGetName(expr: BinaryenExpressionRef): String
  def BinaryenBreakSetName(expr: BinaryenExpressionRef, name: String): Unit
  def BinaryenBreakGetCondition(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenBreakSetCondition(expr: BinaryenExpressionRef, condExpr: BinaryenExpressionRef): Unit
  def BinaryenBreakGetValue(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenBreakSetValue(expr: BinaryenExpressionRef, valueExpr: BinaryenExpressionRef): Unit

  // Switch
  // TODO br_table

  // Call
  def BinaryenCallGetTarget(expr: BinaryenExpressionRef): String
  def BinaryenCallSetTarget(expr: BinaryenExpressionRef, target: String): Unit
  def BinaryenCallGetNumOperands(expr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenCallGetOperandAt(expr: BinaryenExpressionRef, index: BinaryenIndex): BinaryenExpressionRef
  def BinaryenCallSetOperandAt(
      expr: BinaryenExpressionRef,
      index: BinaryenIndex,
      operandExpr: BinaryenExpressionRef
  ): Unit
  def BinaryenCallAppendOperand(expr: BinaryenExpressionRef, operandExpr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenCallInsertOperandAt(
      expr: BinaryenExpressionRef,
      index: BinaryenIndex,
      operandExpr: BinaryenExpressionRef
  ): Unit
  def BinaryenCallRemoveOperandAt(expr: BinaryenExpressionRef, index: BinaryenIndex): BinaryenExpressionRef
  def BinaryenCallIsReturn(expr: BinaryenExpressionRef): Boolean
  def BinaryenCallSetReturn(expr: BinaryenExpressionRef, isReturn: Boolean): Unit

  // Call Indirect
  def BinaryenCallIndirectGetTarget(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenCallIndirectSetTarget(expr: BinaryenExpressionRef, targetExpr: BinaryenExpressionRef): Unit
  def BinaryenCallIndirectGetTable(expr: BinaryenExpressionRef): String
  def BinaryenCallIndirectSetTable(expr: BinaryenExpressionRef, table: String): Unit
  def BinaryenCallIndirectGetNumOperands(expr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenCallIndirectGetOperandAt(expr: BinaryenExpressionRef, index: BinaryenIndex): BinaryenExpressionRef
  def BinaryenCallIndirectSetOperandAt(
      expr: BinaryenExpressionRef,
      index: BinaryenIndex,
      operandExpr: BinaryenExpressionRef
  ): Unit
  def BinaryenCallIndirectAppendOperand(expr: BinaryenExpressionRef, operandExpr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenCallIndirectInsertOperandAt(
      expr: BinaryenExpressionRef,
      index: BinaryenIndex,
      operandExpr: BinaryenExpressionRef
  ): Unit
  def BinaryenCallIndirectRemoveOperandAt(expr: BinaryenExpressionRef, index: BinaryenIndex): BinaryenExpressionRef
  def BinaryenCallIndirectIsReturn(expr: BinaryenExpressionRef): Boolean
  def BinaryenCallIndirectSetReturn(expr: BinaryenExpressionRef, isReturn: Boolean): Unit
  def BinaryenCallIndirectGetParams(expr: BinaryenExpressionRef): BinaryenType
  def BinaryenCallIndirectSetParams(expr: BinaryenExpressionRef, params: BinaryenType): Unit
  def BinaryenCallIndirectGetResults(expr: BinaryenExpressionRef): BinaryenType
  def BinaryenCallIndirectSetResults(expr: BinaryenExpressionRef, results: BinaryenType): Unit

  // Local Get
  def BinaryenLocalGetGetIndex(expr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenLocalGetSetIndex(expr: BinaryenExpressionRef, index: BinaryenIndex): Unit

  // Local Set
  def BinaryenLocalSetIsTee(expr: BinaryenExpressionRef): Boolean
  def BinaryenLocalSetGetIndex(expr: BinaryenExpressionRef): BinaryenIndex
  def BinaryenLocalSetSetIndex(expr: BinaryenExpressionRef, index: BinaryenIndex): Unit
  def BinaryenLocalSetGetValue(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenLocalSetSetValue(expr: BinaryenExpressionRef, valueExpr: BinaryenExpressionRef): Unit

  // Global Get
  def BinaryenGlobalGetGetName(expr: BinaryenExpressionRef): String
  def BinaryenGlobalGetSetName(expr: BinaryenExpressionRef, name: String): Unit

  // Global Set
  def BinaryenGlobalSetGetName(expr: BinaryenExpressionRef): String
  def BinaryenGlobalSetSetName(expr: BinaryenExpressionRef, name: String): Unit
  def BinaryenGlobalSetGetValue(expr: BinaryenExpressionRef): BinaryenExpressionRef
  def BinaryenGlobalSetSetValue(expr: BinaryenExpressionRef, valueExpr: BinaryenExpressionRef): Unit

  // TODO
  // TableGet
  // TableSet
  // TableSize
  // TableGrow
  // MemoryGlow
  // Load
  // Store
  // Const
  // Unary
  // Binary
  // Select
  // Drop
  // Return
  // AtomicCmpxchg
  // AtomicWait
  // AtomicNotify
  // AtomicFence
  // SIMDExtract
  // SIMDReplace
  // SIMDShuffle
  // SIMDTernary
  // SIMDShift
  // SIMDLoad
  // SIMDLoadStoreLane
  // MemoryInit
  // DataDrop
  // MemoryFill
  // RefIsNull
  // RefAs
  // RefFunc
  // RefEq
  // Try
  // Throw
  // Rethrow
  // TupleMake
  // TupleExtract
  // RefI31
  // I31Get
  // CallRef
  // RefTest
  // RefCast
  // BrOn
  // StructNew
  // StructGet
  // StructSet
  // ArrayNew
  // ArrayNewFixed
  // ArrayGet
  // ArraySet
  // ArrayLen
  // ArrayCopy
  // StringNew
  // StringConst
  // StringMeasure
  // StringEncode
  // StringConcat
  // StringEq
  // StringAs
  // StringWTF8Advance
  // StringWTF16Get
  // StringIterNext
  // StringIterMove
  // StringSliceWTF
  // StringSliceIter

// Functions
  /** Adds a function to the module. This is thread-safe.
    * @param varTypes
    *   the types of variables. In WebAssembly, vars share an index space with params. In other words, params come from
    *   the function type, and vars are provided in this call, and together they are all the locals. The order is first
    *   params and then vars, so if you have one param it will be at index 0 (and written $0), and if you also have 2
    *   vars they will be at indexes 1 and 2, etc., that is, they share an index space.
    */
  def BinaryenAddFunction(
      module: BinaryenModuleRef,
      name: String,
      params: BinaryenType,
      results: BinaryenType,
      varTypes: Array[BinaryenType],
      numVarTypes: BinaryenIndex,
      body: BinaryenExpressionRef
  ): BinaryenFunctionRef

  /** As BinaryenAddFunction, but takes a HeapType rather than params and results.
    *
    * This lets you set the specific type of the function.
    */
  def BinaryenAddFunctionWithHeapType(
      module: BinaryenModuleRef,
      name: String,
      `type`: BinaryenHeapType,
      varTypes: Array[BinaryenType],
      numVarTypes: BinaryenIndex,
      body: BinaryenExpressionRef
  ): BinaryenFunctionRef

  /** Gets a function reference by name. Returns NULL if the function does not exist. */
  def BinaryenGetFunction(module: BinaryenModuleRef, name: String): BinaryenFunctionRef

  /** Removes a function by name. */
  def BinaryenRemoveFunction(module: BinaryenModuleRef, name: String): Unit

  /** Gets the number of functions in the module. */
  def BinaryenGetNumFunctions(module: BinaryenModuleRef): BinaryenIndex

  /** Gets the function at the specified index. */
  def BinaryenGetFunctionByIndex(module: BinaryenModuleRef, index: BinaryenIndex): BinaryenFunctionRef

  def BinaryenAddFunctionImport(
      module: BinaryenModuleRef,
      internalName: String,
      externalModuleName: String,
      externalBaseName: String,
      params: BinaryenType,
      results: BinaryenType
  ): Unit

  def BinaryenAddTableImport(
      module: BinaryenModuleRef,
      internalName: String,
      externalModuleName: String,
      externalBaseName: String
  ): Unit

  def BinaryenAddMemoryImport(
      module: BinaryenModuleRef,
      internalName: String,
      externalModuleName: String,
      externalBaseName: String,
      shared: Byte
  ): Unit

  def BinaryenAddGlobalImport(
      module: BinaryenModuleRef,
      internalName: String,
      externalModuleName: String,
      externalBaseName: String,
      globalType: BinaryenType,
      mutable: Boolean
  ): Unit

  def BinaryenAddTagImport(
      module: BinaryenModuleRef,
      internalName: String,
      externalModuleName: String,
      externalBaseName: String,
      params: BinaryenType,
      results: BinaryenType
  ): Unit

  /** Adds a function export to the module. */
  def BinaryenAddFunctionExport(
      module: BinaryenModuleRef,
      internalName: String,
      externalName: String
  ): BinaryenExportRef

  /** Adds a table export to the module. */
  def BinaryenAddTableExport(
      module: BinaryenModuleRef,
      internalName: String,
      externalName: String
  ): BinaryenExportRef

  /** Adds a memory export to the module. */
  def BinaryenAddMemoryExport(
      module: BinaryenModuleRef,
      internalName: String,
      externalName: String
  ): BinaryenExportRef

  /** Adds a global export to the module. */
  def BinaryenAddGlobalExport(
      module: BinaryenModuleRef,
      internalName: String,
      externalName: String
  ): BinaryenExportRef

  /** Adds a tag export to the module. */
  def BinaryenAddTagExport(
      module: BinaryenModuleRef,
      internalName: String,
      externalName: String
  ): BinaryenExportRef

  /** Gets an export reference by external name. Returns NULL if the export does not exist.
    */
  def BinaryenGetExport(
      module: BinaryenModuleRef,
      externalName: String
  ): BinaryenExportRef

  /** Removes an export by external name. */
  def BinaryenRemoveExport(
      module: BinaryenModuleRef,
      externalName: String
  ): Unit

  /** Gets the number of exports in the module. */
  def BinaryenGetNumExports(
      module: BinaryenModuleRef
  ): BinaryenIndex

  /** Gets the export at the specified index. */
  def BinaryenGetExportByIndex(
      module: BinaryenModuleRef,
      index: BinaryenIndex
  ): BinaryenExportRef

  /** Adds a global to the module. */
  def BinaryenAddGlobal(
      module: BinaryenModuleRef,
      name: String,
      `type`: BinaryenType,
      mutable: Boolean,
      init: BinaryenExpressionRef
  ): BinaryenGlobalRef

  /** Gets a global reference by name. Returns NULL if the global does not exist. */
  def BinaryenGetGlobal(
      module: BinaryenModuleRef,
      name: String
  ): BinaryenGlobalRef

  /** Removes a global by name. */
  def BinaryenRemoveGlobal(
      module: BinaryenModuleRef,
      name: String
  ): Unit

  /** Gets the number of globals in the module. */
  def BinaryenGetNumGlobals(
      module: BinaryenModuleRef
  ): BinaryenIndex

  /** Gets the global at the specified index. */
  def BinaryenGetGlobalByIndex(
      module: BinaryenModuleRef,
      index: BinaryenIndex
  ): BinaryenGlobalRef

  // Tags
  /** Adds a tag to the module. */
  def BinaryenAddTag(
      module: BinaryenModuleRef,
      name: String,
      params: BinaryenType,
      results: BinaryenType
  ): BinaryenTagRef

  /** Gets a tag reference by name. */
  def BinaryenGetTag(
      module: BinaryenModuleRef,
      name: String
  ): BinaryenTagRef

  /** Removes a tag by name. */
  def BinaryenRemoveTag(
      module: BinaryenModuleRef,
      name: String
  ): Unit

  // Tables

  /** Adds a table to the module. */
  def BinaryenAddTable(
      module: BinaryenModuleRef,
      table: String,
      initial: BinaryenIndex,
      maximum: BinaryenIndex,
      tableType: BinaryenType
  ): BinaryenTableRef

  /** Removes a table by name. */
  def BinaryenRemoveTable(
      module: BinaryenModuleRef,
      table: String
  ): Unit

  /** Gets the number of tables in the module. */
  def BinaryenGetNumTables(module: BinaryenModuleRef): BinaryenIndex

  /** Gets a table reference by name. */
  def BinaryenGetTable(
      module: BinaryenModuleRef,
      name: String
  ): BinaryenTableRef

  /** Gets a table reference by index. */
  def BinaryenGetTableByIndex(
      module: BinaryenModuleRef,
      index: BinaryenIndex
  ): BinaryenTableRef

  /** Adds an active element segment to the module. */
  def BinaryenAddActiveElementSegment(
      module: BinaryenModuleRef,
      table: String,
      name: String,
      funcNames: Array[String],
      numFuncNames: BinaryenIndex,
      offset: BinaryenExpressionRef
  ): BinaryenElementSegmentRef

  /** Adds a passive element segment to the module. */
  def BinaryenAddPassiveElementSegment(
      module: BinaryenModuleRef,
      name: String,
      funcNames: Array[String],
      numFuncNames: BinaryenIndex
  ): BinaryenElementSegmentRef

  /** Removes an element segment by name. */
  def BinaryenRemoveElementSegment(
      module: BinaryenModuleRef,
      name: String
  ): Unit

  /** Gets the number of element segments in the module. */
  def BinaryenGetNumElementSegments(
      module: BinaryenModuleRef
  ): BinaryenIndex

  /** Gets an element segment reference by name. */
  def BinaryenGetElementSegment(
      module: BinaryenModuleRef,
      name: String
  ): BinaryenElementSegmentRef

  /** Gets an element segment reference by index. */
  def BinaryenGetElementSegmentByIndex(
      module: BinaryenModuleRef,
      index: BinaryenIndex
  ): BinaryenElementSegmentRef

  /** Creates a memory, overwriting any existing memory.
    *
    * Each memory has data in segments, a start offset in segmentOffsets, and a size in segmentSizes. exportName can be
    * NULL.
    */
  def BinaryenSetMemory(
      module: BinaryenModuleRef,
      initial: BinaryenIndex,
      maximum: BinaryenIndex,
      exportName: String,
      segments: Array[String],
      segmentPassive: Array[Boolean],
      segmentOffsets: Array[BinaryenExpressionRef],
      segmentSizes: Array[BinaryenIndex],
      numSegments: BinaryenIndex,
      shared: Boolean,
      memory64: Boolean,
      name: String
  ): Unit

  /** Checks if the module has a memory. */
  def BinaryenHasMemory(module: BinaryenModuleRef): Boolean

  /** Gets the initial size of the memory. */
  def BinaryenMemoryGetInitial(module: BinaryenModuleRef, name: String): BinaryenIndex

  /** Checks if the memory has a maximum size. */
  def BinaryenMemoryHasMax(module: BinaryenModuleRef, name: String): Boolean

  /** Gets the maximum size of the memory. */
  def BinaryenMemoryGetMax(module: BinaryenModuleRef, name: String): BinaryenIndex

  /** Gets the module name of an imported memory. */
  def BinaryenMemoryImportGetModule(module: BinaryenModuleRef, name: String): String

  /** Gets the base name of an imported memory. */
  def BinaryenMemoryImportGetBase(module: BinaryenModuleRef, name: String): String

  /** Checks if the memory is shared. */
  def BinaryenMemoryIsShared(module: BinaryenModuleRef, name: String): Boolean

  /** Checks if the memory is 64-bit. */
  def BinaryenMemoryIs64(module: BinaryenModuleRef, name: String): Boolean

  /** Gets the number of memory segments in the module. */
  // def BinaryenGetNumMemorySegments(module: BinaryenModuleRef): UInt

  /** Gets the byte offset of a memory segment. */
  // def BinaryenGetMemorySegmentByteOffset(module: BinaryenModuleRef, id: BinaryenIndex): UInt

  /** Gets the byte length of a memory segment. */
  // def BinaryenGetMemorySegmentByteLength(module: BinaryenModuleRef, id: BinaryenIndex): SizeT

  /** Checks if a memory segment is passive. */
  def BinaryenGetMemorySegmentPassive(module: BinaryenModuleRef, id: BinaryenIndex): Boolean

  /** Copies the data of a memory segment into a buffer. */
  def BinaryenCopyMemorySegmentData(module: BinaryenModuleRef, id: BinaryenIndex, buffer: Array[Byte]): Unit

  // TODO
  // Start function. One per module
  // BINARYEN_API void BinaryenSetStart(BinaryenModuleRef module,
  //                                    BinaryenFunctionRef start);
  //
  // // Features
  //
  // // These control what features are allowed when validation and in passes.
  // BINARYEN_API BinaryenFeatures
  // BinaryenModuleGetFeatures(BinaryenModuleRef module);
  // BINARYEN_API void BinaryenModuleSetFeatures(BinaryenModuleRef module,
  //                                             BinaryenFeatures features);

  //
  // === Module operations ===
  //
  /** Parse a module in s-expression text format */
  def BinaryenModuleParse(text: String): BinaryenModuleRef

  /** Print a module to stdout in s-expression text format. Useful for debugging.
    */
  def BinaryenModulePrint(module: BinaryenModuleRef): Unit

  /** Print a module to stdout in stack IR text format. Useful for debugging. */
  def BinaryenModulePrintStackIR(
      module: BinaryenModuleRef,
      optimize: Boolean
  ): Unit

  /** Print a module to stdout in asm.js syntax. */
  def BinaryenModulePrintAsmjs(module: BinaryenModuleRef): Unit

  /** Validate a module, showing errors on problems.
    * @return
    *   0 if an error occurred, 1 if validated succesfully
    */
  def BinaryenModuleValidate(module: BinaryenModuleRef): Boolean

  /** Runs the standard optimization passes on the module. Uses the currently set global optimize and shrink level.
    */
  def BinaryenModuleOptimize(module: BinaryenModuleRef): Unit

  /** Updates the internal name mapping logic in a module. This must be called after renaming module elements.
    */
  def BinaryenModuleUpdateMaps(module: BinaryenModuleRef): Unit

  /** Gets the currently set optimize level. Applies to all modules, globally. 0, 1, 2 correspond to -O0, -O1, -O2
    * (default), etc.
    */
  def BinaryenGetOptimizeLevel(): Int

  /** Sets the optimization level to use. Applies to all modules, globally. 0, 1, 2 correspond to -O0, -O1, -O2
    * (default), etc.
    */
  def BinaryenSetOptimizeLevel(level: Int): Unit

  /** Gets the currently set shrink level. Applies to all modules, globally. 0, 1, 2 correspond to -O0, -Os (default),
    * -Oz.
    */
  def BinaryenGetShrinkLevel(): Int

  /** Sets the shrink level to use. Applies to all modules, globally. 0, 1, 2 correspond to -O0, -Os (default), -Oz.
    */
  def BinaryenSetShrinkLevel(level: Int): Unit

  /** Gets whether generating debug information is currently enabled or not. Applies to all modules, globally.
    */
  def BinaryenGetDebugInfo(): Boolean

  /** Enables or disables debug information in emitted binaries. Applies to all modules, globally.
    */
  def BinaryenSetDebugInfo(on: Boolean): Unit

  /** Gets whether the low 1K of memory can be considered unused when optimizing. Applies to all modules, globally.
    */
  def BinaryenGetLowMemoryUnused(): Boolean

  /** Enables or disables whether the low 1K of memory can be considered unused when optimizing. Applies to all modules,
    * globally.
    */
  def BinaryenSetLowMemoryUnused(on: Boolean): Unit

  /** Gets whether to assume that an imported memory is zero-initialized.
    */
  def BinaryenGetZeroFilledMemory(): Boolean

  /** Enables or disables whether to assume that an imported memory is zero-initialized. Applies to all modules,
    * globally.
    */
  def BinaryenSetZeroFilledMemory(on: Boolean): Unit

  /** Gets whether fast math optimizations are enabled, ignoring for example corner cases of floating-point math like
    * NaN changes.
    */
  def BinaryenGetFastMath(): Boolean

  /** Enables or disables fast math optimizations, ignoring for example corner cases of floating-point math like NaN
    * changes. Applies to all modules, globally.
    */
  def BinaryenSetFastMath(value: Boolean): Unit

  /** Gets the value of the specified arbitrary pass argument. Applies to all modules, globally.
    */
  def BinaryenGetPassArgument(name: String): String

  /** Sets the value of the specified arbitrary pass argument. Removes the respective argument if `value` is NULL.
    * Applies to all modules, globally.
    */
  def BinaryenSetPassArgument(name: String, value: String): Unit

  /** Clears all arbitrary pass arguments. Applies to all modules, globally.
    */
  def BinaryenClearPassArguments(): Unit

  /** Gets the function size at which we always inline. Applies to all modules, globally.
    *
    * @return
    *   The function size at which we always inline.
    */
  def BinaryenGetAlwaysInlineMaxSize(): BinaryenIndex

  /** Sets the function size at which we always inline. Applies to all modules, globally.
    */
  def BinaryenSetAlwaysInlineMaxSize(size: BinaryenIndex): Unit

  /** Gets the function size which we inline when functions are lightweight. Applies to all modules, globally.
    */
  def BinaryenGetFlexibleInlineMaxSize(): BinaryenIndex

  /** Sets the function size which we inline when functions are lightweight. Applies to all modules, globally.
    */
  def BinaryenSetFlexibleInlineMaxSize(size: BinaryenIndex): Unit

  /** Gets the function size which we inline when there is only one caller. Applies to all modules, globally.
    */
  def BinaryenGetOneCallerInlineMaxSize(): BinaryenIndex

  /** Sets the function size which we inline when there is only one caller. Applies to all modules, globally.
    */
  def BinaryenSetOneCallerInlineMaxSize(size: BinaryenIndex): Unit

  /** Gets whether functions with loops are allowed to be inlined. Applies to all modules, globally.
    */
  def BinaryenGetAllowInliningFunctionsWithLoops(): Boolean

  /** Sets whether functions with loops are allowed to be inlined. Applies to all modules, globally.
    */
  def BinaryenSetAllowInliningFunctionsWithLoops(enabled: Boolean): Unit

  /** Runs the specified passes on the module. Uses the currently set global optimize and shrink level.
    */
  def BinaryenModuleRunPasses(
      module: BinaryenModuleRef,
      passes: Array[String],
      numPasses: BinaryenIndex
  ): Unit

  /** Auto-generate drop() operations where needed. This lets you generate code without worrying about where they are
    * needed. (It is more efficient to do it yourself, but simpler to use autodrop).
    */
  def BinaryenModuleAutoDrop(module: BinaryenModuleRef): Unit

  /** Serialize a module into binary form. Uses the currently set global debugInfo option.
    *
    * @return
    *   how many bytes were written. This will be less than or equal to outputSize
    */
  def BinaryenModuleWrite(
      module: BinaryenModuleRef,
      output: Array[Byte],
      outputSize: Int
  ): Int

  /** Serialize a module in s-expression text format.
    *
    * @return
    *   how many bytes were written. This will be less than or equal to outputSize
    */
  def BinaryenModuleWriteText(
      module: BinaryenModuleRef,
      output: Array[Byte],
      outputSize: Int
  ): Int

  /** Serialize a module in stack IR text format.
    * @return
    *   how many bytes were written. This will be less than or equal to outputSize
    */
  def BinaryenModuleWriteStackIR(
      module: BinaryenModuleRef,
      output: Array[Byte],
      outputSize: Int,
      optimize: Boolean
  ): Int

  /** Serialize a module into binary form including its source map. Uses the currently set global debugInfo option.
    * @return
    *   How many bytes were written. This will be less than or equal to outputSize.
    */
  def BinaryenModuleWriteWithSourceMap(
      module: BinaryenModuleRef,
      url: String,
      output: Array[Byte],
      outputSize: Int,
      sourceMap: Array[Byte],
      sourceMapSize: Int
  ): BinaryenBufferSizes

  /** Serializes a module into binary form, optionally including its source map if sourceMapUrl has been specified. Uses
    * the currently set global debugInfo option. Differs from BinaryenModuleWrite in that it implicitly allocates
    * appropriate buffers using malloc(), and expects the user to free() them manually once not needed anymore.
    */
  def BinaryenModuleAllocateAndWrite(
      module: BinaryenModuleRef,
      sourceMapUrl: String
  ): BinaryenModuleAllocateAndWriteResult

  /** Serialize a module in s-expression form. Implicity allocates the returned char* with malloc(), and expects the
    * user to free() them manually once not needed anymore.
    */
  def BinaryenModuleAllocateAndWriteText(
      module: BinaryenModuleRef
  ): String

  /** Serialize a module in stack IR form. Implicitly allocates the returned char* with malloc(), and expects the user
    * to free() them manually once not needed anymore
    */
  def BinaryenModuleAllocateAndWriteStackIR(
      module: BinaryenModuleRef,
      optimize: Boolean
  ): String

  /** Deserialize a module from binary form. */
  def BinaryenModuleRead(input: Array[Byte], inputSize: Int): BinaryenModuleRef

  /** Execute a module in the Binaryen interpreter. This will create an instance of the module, run it in the
    * interpreter - which means running the start method
    *   - and then destroying the instance.
    */
  def BinaryenModuleInterpret(module: BinaryenModuleRef): Unit

  /** Adds a debug info file name to the module and returns its index. */
  def BinaryenModuleAddDebugInfoFileName(
      module: BinaryenModuleRef,
      filename: String
  ): BinaryenIndex

  /** Gets the name of the debug info file at the specified index. Returns `NULL` if it does not exist. */
  def BinaryenModuleGetDebugInfoFileName(
      module: BinaryenModuleRef,
      index: BinaryenIndex
  ): String

  //
  // ========== Function Operations ==========
  //

  /** Gets the name of the specified `Function`. */
  def BinaryenFunctionGetName(func: BinaryenFunctionRef): String

  /** Gets the type of the parameter at the specified index of the specified `Function`. */
  def BinaryenFunctionGetParams(func: BinaryenFunctionRef): BinaryenType

  /** Gets the result type of the specified `Function`. */
  def BinaryenFunctionGetResults(func: BinaryenFunctionRef): BinaryenType

  /** Gets the number of additional locals within the specified `Function`. */
  def BinaryenFunctionGetNumVars(func: BinaryenFunctionRef): BinaryenIndex

  /** Gets the type of the additional local at the specified index within the specified `Function`. */
  def BinaryenFunctionGetVar(func: BinaryenFunctionRef, index: BinaryenIndex): BinaryenType

  /** Appends a local variable to the specified `Function`, returning its index. */
  def BinaryenFunctionAddVar(func: BinaryenFunctionRef, `type`: BinaryenType): BinaryenIndex

  /** Gets the number of locals within the specified function. Includes parameters. */
  def BinaryenFunctionGetNumLocals(func: BinaryenFunctionRef): BinaryenIndex

  /** Tests if the local at the specified index has a name. */
  def BinaryenFunctionHasLocalName(func: BinaryenFunctionRef, index: BinaryenIndex): Boolean

  /** Gets the name of the local at the specified index. */
  def BinaryenFunctionGetLocalName(func: BinaryenFunctionRef, index: BinaryenIndex): String

  /** Sets the name of the local at the specified index. */
  def BinaryenFunctionSetLocalName(func: BinaryenFunctionRef, index: BinaryenIndex, name: String): Unit

  /** Gets the body of the specified `Function`. */
  def BinaryenFunctionGetBody(func: BinaryenFunctionRef): BinaryenExpressionRef

  /** Sets the body of the specified `Function`. */
  def BinaryenFunctionSetBody(func: BinaryenFunctionRef, body: BinaryenExpressionRef): Unit

  /** Runs the standard optimization passes on the function. Uses the currently set global optimize and shrink level. */
  def BinaryenFunctionOptimize(func: BinaryenFunctionRef, module: BinaryenModuleRef): Unit

  /** Runs the specified passes on the function. Uses the currently set global optimize and shrink level. */
  def BinaryenFunctionRunPasses(
      func: BinaryenFunctionRef,
      module: BinaryenModuleRef,
      passes: Array[String],
      numPasses: BinaryenIndex
  ): Unit

  /** Sets the debug location of the specified `Expression` within the specified `Function`. */
  def BinaryenFunctionSetDebugLocation(
      func: BinaryenFunctionRef,
      expr: BinaryenExpressionRef,
      fileIndex: BinaryenIndex,
      lineNumber: BinaryenIndex,
      columnNumber: BinaryenIndex
  ): Unit

  //
  // ========== Table Operations ==========
  //
  /** Gets the name of the specified `Table`. */
  def BinaryenTableGetName(table: BinaryenTableRef): String

  /** Sets the name of the specified `Table`. */
  def BinaryenTableSetName(table: BinaryenTableRef, name: String): Unit

  /** Gets the initial number of pages of the specified `Table`. */
  def BinaryenTableGetInitial(table: BinaryenTableRef): BinaryenIndex

  /** Sets the initial number of pages of the specified `Table`. */
  def BinaryenTableSetInitial(table: BinaryenTableRef, initial: BinaryenIndex): Unit

  /** Tests whether the specified `Table` has a maximum number of pages. */
  def BinaryenTableHasMax(table: BinaryenTableRef): Boolean

  /** Gets the maximum number of pages of the specified `Table`. */
  def BinaryenTableGetMax(table: BinaryenTableRef): BinaryenIndex

  /** Sets the maximum number of pages of the specified `Table`. */
  def BinaryenTableSetMax(table: BinaryenTableRef, max: BinaryenIndex): Unit

  /** Gets the table type of the specified `Table`. */
  def BinaryenTableGetType(table: BinaryenTableRef): BinaryenType

  /** Sets the table type of the specified `Table`. */
  def BinaryenTableSetType(table: BinaryenTableRef, tableType: BinaryenType): Unit

  //
  // ========== Elem Segment Operations ==========
  //
  /** Gets the name of the specified `ElementSegment`. */
  def BinaryenElementSegmentGetName(elem: BinaryenElementSegmentRef): String

  /** Sets the name of the specified `ElementSegment`. */
  def BinaryenElementSegmentSetName(elem: BinaryenElementSegmentRef, name: String): Unit

  /** Gets the table name of the specified `ElementSegment`. */
  def BinaryenElementSegmentGetTable(elem: BinaryenElementSegmentRef): String

  /** Sets the table name of the specified `ElementSegment`. */
  def BinaryenElementSegmentSetTable(elem: BinaryenElementSegmentRef, table: String): Unit

  /** Gets the segment offset in case of active segments. */
  def BinaryenElementSegmentGetOffset(elem: BinaryenElementSegmentRef): BinaryenExpressionRef

  /** Gets the length of items in the segment. */
  def BinaryenElementSegmentGetLength(elem: BinaryenElementSegmentRef): BinaryenIndex

  /** Gets the item at the specified index. */
  def BinaryenElementSegmentGetData(elem: BinaryenElementSegmentRef, dataId: BinaryenIndex): String

  /** Returns true if the specified elem segment is passive. */
  def BinaryenElementSegmentIsPassive(elem: BinaryenElementSegmentRef): Boolean

  //
  // ========== Global Operations ==========
  //

  /** Gets the name of the specified `Global`. */
  def BinaryenGlobalGetName(global: BinaryenGlobalRef): String

  /** Gets the name of the `GlobalType` associated with the specified `Global`. May be `NULL` if the signature is
    * implicit.
    */
  def BinaryenGlobalGetType(global: BinaryenGlobalRef): BinaryenType

  /** Returns true if the specified `Global` is mutable. */
  def BinaryenGlobalIsMutable(global: BinaryenGlobalRef): Boolean

  /** Gets the initialization expression of the specified `Global`. */
  def BinaryenGlobalGetInitExpr(global: BinaryenGlobalRef): BinaryenExpressionRef

  //
  // ========== Tag Operations ==========
  //
  /** Gets the name of the specified `Tag`. */
  def BinaryenTagGetName(tag: BinaryenTagRef): String

  /** Gets the parameters type of the specified `Tag`. */
  def BinaryenTagGetParams(tag: BinaryenTagRef): BinaryenType

  /** Gets the results type of the specified `Tag`. */
  def BinaryenTagGetResults(tag: BinaryenTagRef): BinaryenType

  //
  // ========== Import Operations ==========
  //
  /** Gets the external module name of the specified function import. */
  def BinaryenFunctionImportGetModule(`import`: BinaryenFunctionRef): String

  /** Gets the external module name of the specified table import. */
  def BinaryenTableImportGetModule(`import`: BinaryenTableRef): String

  /** Gets the external module name of the specified global import. */
  def BinaryenGlobalImportGetModule(`import`: BinaryenGlobalRef): String

  /** Gets the external module name of the specified tag import. */
  def BinaryenTagImportGetModule(`import`: BinaryenTagRef): String

  /** Gets the external base name of the specified function import. */
  def BinaryenFunctionImportGetBase(`import`: BinaryenFunctionRef): String

  /** Gets the external base name of the specified table import. */
  def BinaryenTableImportGetBase(`import`: BinaryenTableRef): String

  /** Gets the external base name of the specified global import. */
  def BinaryenGlobalImportGetBase(`import`: BinaryenGlobalRef): String

  /** Gets the external base name of the specified tag import. */
  def BinaryenTagImportGetBase(`import`: BinaryenTagRef): String

  //
  // ========== Export Operations ==========
  //
  /** Gets the external kind of the specified export. */
  def BinaryenExportGetKind(`export`: BinaryenExportRef): BinaryenExternalKind

  /** Gets the external name of the specified export. */
  def BinaryenExportGetName(`export`: BinaryenExportRef): String

  /** Gets the internal name of the specified export. */
  def BinaryenExportGetValue(`export`: BinaryenExportRef): String

  //
  // ========= Custom sections =========
  //
  /** Adds a custom section to the module. */
  def BinaryenAddCustomSection(
      module: BinaryenModuleRef,
      name: String,
      contents: String,
      contentsSize: BinaryenIndex
  ): Unit

  //
  // ========= Effect analyzer =========
  //

  /** Returns a side effect indicating no specific side effects. */
  def BinaryenSideEffectNone(): BinaryenSideEffects

  /** Returns a side effect indicating branches. */
  def BinaryenSideEffectBranches(): BinaryenSideEffects

  /** Returns a side effect indicating calls. */
  def BinaryenSideEffectCalls(): BinaryenSideEffects

  /** Returns a side effect indicating reads from local variables. */
  def BinaryenSideEffectReadsLocal(): BinaryenSideEffects

  /** Returns a side effect indicating writes to local variables. */
  def BinaryenSideEffectWritesLocal(): BinaryenSideEffects

  /** Returns a side effect indicating reads from global variables. */
  def BinaryenSideEffectReadsGlobal(): BinaryenSideEffects

  /** Returns a side effect indicating writes to global variables. */
  def BinaryenSideEffectWritesGlobal(): BinaryenSideEffects

  /** Returns a side effect indicating reads from memory. */
  def BinaryenSideEffectReadsMemory(): BinaryenSideEffects

  /** Returns a side effect indicating writes to memory. */
  def BinaryenSideEffectWritesMemory(): BinaryenSideEffects

  /** Returns a side effect indicating reads from tables. */
  def BinaryenSideEffectReadsTable(): BinaryenSideEffects

  /** Returns a side effect indicating writes to tables. */
  def BinaryenSideEffectWritesTable(): BinaryenSideEffects

  /** Returns a side effect indicating implicit traps. */
  def BinaryenSideEffectImplicitTrap(): BinaryenSideEffects

  /** Returns a side effect indicating traps never happen. */
  def BinaryenSideEffectTrapsNeverHappen(): BinaryenSideEffects

  /** Returns a side effect indicating atomic operations. */
  def BinaryenSideEffectIsAtomic(): BinaryenSideEffects

  /** Returns a side effect indicating throw. */
  def BinaryenSideEffectThrows(): BinaryenSideEffects

  /** Returns a side effect indicating dangling pop. */
  def BinaryenSideEffectDanglingPop(): BinaryenSideEffects

  /** Returns a side effect indicating any side effect. */
  def BinaryenSideEffectAny(): BinaryenSideEffects

  /** Gets the side effects of the specified expression within the specified module.
    */
  def BinaryenExpressionGetSideEffects(expr: BinaryenExpressionRef, module: BinaryenModuleRef): BinaryenSideEffects

  //
  // ========== CFG / Relooper ==========
  //
  // General usage is (1) create a relooper, (2) create blocks, (3) add
  // branches between them, (4) render the output.
  //
  // For more details, see src/cfg/Relooper.h and
  // https://github.com/WebAssembly/binaryen/wiki/Compiling-to-WebAssembly-with-Binaryen#cfg-api

  /** Creates a `Relooper` instance.
    */
  def RelooperCreate(module: BinaryenModuleRef): RelooperRef

  /** Creates a basic block that ends with nothing, or with some simple branching.
    */
  def RelooperAddBlock(relooper: RelooperRef, code: BinaryenExpressionRef): RelooperBlockRef

  /** Creates a branch to another basic block.
    *
    * The branch can have code on it, which is executed as the branch happens. This is useful for phis. Otherwise, code
    * can be `NULL`.
    */
  def RelooperAddBranch(
      from: RelooperBlockRef,
      to: RelooperBlockRef,
      condition: BinaryenExpressionRef,
      code: BinaryenExpressionRef
  ): Unit

  /** Creates a basic block that ends a switch on a condition.
    */
  def RelooperAddBlockWithSwitch(
      relooper: RelooperRef,
      code: BinaryenExpressionRef,
      condition: BinaryenExpressionRef
  ): RelooperBlockRef

  /** Creates a switch-style branch to another basic block.
    *
    * The block's switch table will have these indexes going to that target.
    */
  def RelooperAddBranchForSwitch(
      from: RelooperBlockRef,
      to: RelooperBlockRef,
      indexes: Array[BinaryenIndex],
      numIndexes: BinaryenIndex,
      code: BinaryenExpressionRef
  ): Unit

  /** Generates structured WebAssembly control flow from the CFG of blocks and branches that were created on this
    * `Relooper` instance.
    *
    * This returns the rendered output and disposes of the `Relooper` and its blocks and branches, as they are no longer
    * needed.
    *
    * @param labelHelper
    *   To render irreducible control flow, we may need a helper variable to guide us to the right target label. This
    *   value should be an index of an i32 local variable that is free for us to use.
    */
  def RelooperRenderAndDispose(
      relooper: RelooperRef,
      entry: RelooperBlockRef,
      labelHelper: BinaryenIndex
  ): BinaryenExpressionRef

  //
  // ========= ExpressionRunner ==========
  //
  /** By default, just evaluates the expression, i.e., all we want to know is whether it computes down to a concrete
    * value, where it is not necessary to preserve side effects like those of a `local.tee`.
    */
  def ExpressionRunnerFlagsDefault(): ExpressionRunnerFlags

  /** Be very careful to preserve any side effects. For example, if we are intending to replace the expression with a
    * constant afterwards, even if we can technically evaluate down to a constant, we still cannot replace the
    * expression if it also sets a local, which must be preserved in this scenario so subsequent code keeps functioning.
    */
  def ExpressionRunnerFlagsPreserveSideeffects(): ExpressionRunnerFlags

  /** Traverse through function calls, attempting to compute their concrete value. Must not be used in function-parallel
    * scenarios, where the called function might be concurrently modified, leading to undefined behavior. Traversing
    * another function reuses all of this runner's flags.
    */
  def ExpressionRunnerFlagsTraverseCalls(): ExpressionRunnerFlags

  /** Creates an `ExpressionRunner` instance.
    */
  def ExpressionRunnerCreate(
      module: BinaryenModuleRef,
      flags: ExpressionRunnerFlags,
      maxDepth: BinaryenIndex,
      maxLoopIterations: BinaryenIndex
  ): ExpressionRunnerRef

  /** Sets a known local value to use.
    *
    * Order matters if expressions have side effects. For example, if the expression also sets a local, this side effect
    * will also happen (not affected by any flags). Returns `true` if the expression actually evaluates to a constant.
    */
  def ExpressionRunnerSetLocalValue(
      runner: ExpressionRunnerRef,
      index: BinaryenIndex,
      value: BinaryenExpressionRef
  ): Boolean

  /** Sets a known global value to use.
    *
    * Order matters if expressions have side effects. For example, if the expression also sets a local, this side effect
    * will also happen (not affected by any flags). Returns `true` if the expression actually evaluates to a constant.
    */
  def ExpressionRunnerSetGlobalValue(runner: ExpressionRunnerRef, name: String, value: BinaryenExpressionRef): Boolean

  /** Runs the expression and returns the constant value expression it evaluates to, if any. Otherwise returns `NULL`.
    * Also disposes the runner.
    */
  def ExpressionRunnerRunAndDispose(runner: ExpressionRunnerRef, expr: BinaryenExpressionRef): BinaryenExpressionRef

  //
  // ========= TypeBuilder =========
  //

  /** Indicates a cycle in the supertype relation. */
  def TypeBuilderErrorReasonSelfSupertype(): TypeBuilderErrorReason

  /** Indicates that the declared supertype of a type is invalid. */
  def TypeBuilderErrorReasonInvalidSupertype(): TypeBuilderErrorReason

  /** Indicates that the declared supertype is an invalid forward reference. */
  def TypeBuilderErrorReasonForwardSupertypeReference(): TypeBuilderErrorReason

  /** Indicates that a child of a type is an invalid forward reference. */
  def TypeBuilderErrorReasonForwardChildReference(): TypeBuilderErrorReason

  /** Constructs a new type builder that allows for the construction of recursive types. Contains a table of `size`
    * mutable heap types.
    */
  def TypeBuilderCreate(size: BinaryenIndex): TypeBuilderRef

  /** Grows the backing table of the type builder by `count` slots. */
  def TypeBuilderGrow(builder: TypeBuilderRef, count: BinaryenIndex): Unit

  /** Gets the size of the backing table of the type builder. */
  def TypeBuilderGetSize(builder: TypeBuilderRef): BinaryenIndex

  /** Sets the heap type at index `index` to a concrete signature type. Expects temporary tuple types if multiple
    * parameter and/or result types include temporary types.
    */
  def TypeBuilderSetSignatureType(
      builder: TypeBuilderRef,
      index: BinaryenIndex,
      paramTypes: BinaryenType,
      resultTypes: BinaryenType
  ): Unit

  /** Sets the heap type at index `index` to a concrete struct type. */
  def TypeBuilderSetStructType(
      builder: TypeBuilderRef,
      index: BinaryenIndex,
      fieldTypes: Array[BinaryenType],
      fieldPackedTypes: Array[BinaryenPackedType],
      fieldMutables: Array[Boolean],
      numFields: Int
  ): Unit

  /** Sets the heap type at index `index` to a concrete array type. */
  def TypeBuilderSetArrayType(
      builder: TypeBuilderRef,
      index: BinaryenIndex,
      elementType: BinaryenType,
      elementPackedType: BinaryenPackedType,
      elementMutable: Int
  ): Unit

  /** Gets the temporary heap type to use at index `index`. Temporary heap types may only be used to construct temporary
    * types using the type builder.
    */
  def TypeBuilderGetTempHeapType(builder: TypeBuilderRef, index: BinaryenIndex): BinaryenHeapType

  /** Gets a temporary tuple type for use with and owned by the type builder. */
  def TypeBuilderGetTempTupleType(
      builder: TypeBuilderRef,
      types: Array[BinaryenType],
      numTypes: BinaryenIndex
  ): BinaryenType

  /** Gets a temporary reference type for use with and owned by the type builder. */
  def TypeBuilderGetTempRefType(builder: TypeBuilderRef, heapType: BinaryenHeapType, nullable: Int): BinaryenType

  /** Sets the type at `index` to be a subtype of the given super type. */
  def TypeBuilderSetSubType(builder: TypeBuilderRef, index: BinaryenIndex, superType: BinaryenHeapType): Unit

  /** Sets the type at `index` to be open (i.e. non-final). */
  def TypeBuilderSetOpen(builder: TypeBuilderRef, index: BinaryenIndex): Unit

  /** Creates a new recursion group in the range `index` inclusive to `index + length` exclusive. Recursion groups must
    * not overlap.
    */
  def TypeBuilderCreateRecGroup(builder: TypeBuilderRef, index: BinaryenIndex, length: BinaryenIndex): Unit

  /** Builds the heap type hierarchy and disposes the builder. Returns `false` and populates `errorIndex` and
    * `errorReason` on failure.
    */
  def TypeBuilderBuildAndDispose(
      builder: TypeBuilderRef,
      heapTypes: Array[BinaryenHeapType],
      errorIndex: Array[BinaryenIndex],
      errorReason: Array[TypeBuilderErrorReason]
  ): Boolean

  /** Sets the textual name of a compound `heapType`. Has no effect if the type already has a canonical name. */
  def BinaryenModuleSetTypeName(module: BinaryenModuleRef, heapType: BinaryenHeapType, name: String): Unit

  /** Sets the field name of a struct `heapType` at index `index`. */
  def BinaryenModuleSetFieldName(
      module: BinaryenModuleRef,
      heapType: BinaryenHeapType,
      index: BinaryenIndex,
      name: String
  ): Unit

  //
  // ========= Utilities =========
  //
  /** Enable or disable coloring for the Wasm printer. */
  def BinaryenSetColorsEnabled(enabled: Boolean): Unit

  /** Query whether color is enabled for the Wasm printer. */
  def BinaryenAreColorsEnabled(): Boolean

}

object Binaryen {

  // private val binaryenLibraryPath = if (!Platform.isWindows()) {
  //   System.setProperty("jna.library.path", "/usr/local/lib")
  //   "/usr/local/lib/libbinaryen.so"
  // } else {
  //   "binaryen" // Update with appropriate path for Windows
  // }

  def apply(): Binaryen = {
    System.setProperty("jna.library.path", "/opt/homebrew/lib/")
    Native.loadLibrary("binaryen", classOf[Binaryen]).asInstanceOf[Binaryen]
  }
}
