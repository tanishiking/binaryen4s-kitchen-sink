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
        BinaryenLibrary.BinaryenType[] fieldTypes = {b.BinaryenTypeInt32(), b.BinaryenTypeInt32()};
        BinaryenLibrary.BinaryenPackedType[] fieldPackedTypes = {b.BinaryenPackedTypeNotPacked(), b.BinaryenPackedTypeNotPacked()};
        boolean[] fieldMutables = {true, true};
        b.TypeBuilderSetStructType(
            typeBuilder,
            0,
            fieldTypes,
            fieldPackedTypes, 
            fieldMutables,
            2);

        // Arrays to hold the heap types, error index, and error reason
        IntByReference errorIndex = new IntByReference();
        IntByReference errorReason = new IntByReference();
        int[] heapTypes = new int[1];

        // Build and dispose the type builder
        // System.out.println("build and dispose");
        System.out.println("build");
        boolean success = b.TypeBuilderBuildAndDispose(typeBuilder, null, null, null);
        // System.out.println("success?");

        if (success) {
            // Type building was successful
            // Access the resulting heap types in the heapTypes array
            // int tp = heapTypes[0];

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