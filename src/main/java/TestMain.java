import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.*;

public class TestMain {
    public static void main(String[] args) {
        System.setProperty("jna.library.path", "/opt/homebrew/lib/");
        // Example usage

        BinaryenLibrary b = BinaryenLibrary.INSTANCE;

        BinaryenLibrary.BinaryenModuleRef module = b.BinaryenModuleCreate();
        // Create a type builder with a specified size
        BinaryenLibrary.TypeBuilderRef typeBuilder = b.TypeBuilderCreate(1);

        // ... Set up your type builder ...
        System.out.println("set signature type");
        // b.TypeBuilderSetSignatureType(typeBuilder, 0, b.BinaryenTypeInt32(), b.BinaryenTypeInt32());
        b.TypeBuilderSetArrayType(typeBuilder, 0, b.BinaryenTypeInt32(), b.BinaryenPackedTypeNotPacked(), true);


        // BinaryenLibrary.BinaryenType[] fieldTypes = {b.BinaryenTypeInt32(), b.BinaryenTypeInt32()};
        // BinaryenLibrary.BinaryenPackedType[] fieldPackedTypes = {b.BinaryenPackedTypeNotPacked(), b.BinaryenPackedTypeNotPacked()};
        // boolean[] fieldMutables = {true, true};
        // b.TypeBuilderSetStructType(
        //     typeBuilder,
        //     0,
        //     fieldTypes,
        //     fieldPackedTypes, 
        //     fieldMutables,
        //     2);

        // Arrays to hold the heap types, error index, and error reason
        IntByReference errorIndex = new IntByReference();
        IntByReference errorReason = new IntByReference();
        BinaryenLibrary.BinaryenHeapType[] heapTypes = {new BinaryenLibrary.BinaryenHeapType()};

        // Build and dispose the type builder
        // System.out.println("build and dispose");
        System.out.println("build " + heapTypes[0]);
        boolean success = b.TypeBuilderBuildAndDispose(typeBuilder, heapTypes, errorIndex, errorReason);
        // System.out.println("success?");

        if (success) {
            // Type building was successful
            // Access the resulting heap types in the heapTypes array
            System.out.println("heap type" + heapTypes[0]);
            BinaryenLibrary.BinaryenHeapType hp = heapTypes[0];
            System.out.println(b.BinaryenHeapTypeIsBasic(hp));
            System.out.println(b.BinaryenHeapTypeIsSignature(hp));
            System.out.println(b.BinaryenHeapTypeIsStruct(hp));
            System.out.println(b.BinaryenHeapTypeIsArray(hp));
            System.out.println(b.BinaryenHeapTypeIsBottom(hp));
            System.out.println("from heaptype");
            BinaryenLibrary.BinaryenType tp = b.BinaryenTypeFromHeapType(hp, true);

            System.out.println("add global");
            b.BinaryenAddGlobal(module, "arr", tp, true, null);

            // Pointer x = b.BinaryenLocalGet(module, 0, b.BinaryenTypeInt32());
            // Pointer one = b.BinaryenConst(module, b.BinaryenLiteralInt32(1));
            // Pointer add = b.BinaryenBinary(module, b.BinaryenAddInt32(), x, one);

            // System.out.println("add");
            // b.BinaryenAddFunctionWithHeapType(module, "print", tp, null, 0, add);

            System.out.println("print");
            b.BinaryenModulePrint(module);
        } else {
            // Type building failed
            int index = errorIndex.getValue();
            int reason = errorReason.getValue();
            System.out.println("Error at index " + index + " with reason: " + reason);
        }
        b.BinaryenModuleDispose(module);
    }
}