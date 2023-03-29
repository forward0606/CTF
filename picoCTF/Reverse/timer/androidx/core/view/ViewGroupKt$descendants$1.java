package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewGroup.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Landroid/view/View;"}, k = 3, mv = {1, 5, 1}, xi = 48)
@DebugMetadata(c = "androidx.core.view.ViewGroupKt$descendants$1", f = "ViewGroup.kt", i = {0, 0, 0, 1, 1}, l = {97, 99}, m = "invokeSuspend", n = {"$this$sequence", "$this$forEach$iv", "child", "$this$sequence", "$this$forEach$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
/* loaded from: classes.dex */
public final class ViewGroupKt$descendants$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super View>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ViewGroup $this_descendants;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewGroupKt$descendants$1(ViewGroup viewGroup, Continuation<? super ViewGroupKt$descendants$1> continuation) {
        super(2, continuation);
        this.$this_descendants = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ViewGroupKt$descendants$1 viewGroupKt$descendants$1 = new ViewGroupKt$descendants$1(this.$this_descendants, continuation);
        viewGroupKt$descendants$1.L$0 = obj;
        return viewGroupKt$descendants$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super View> sequenceScope, Continuation<? super Unit> continuation) {
        return ((ViewGroupKt$descendants$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004e, code lost:
        r7 = r5;
        r5 = r5 + 1;
        r9 = r3.getChildAt(r7);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, "getChildAt(index)");
        r1.L$0 = r2;
        r1.L$1 = r3;
        r1.L$2 = r9;
        r1.I$0 = r5;
        r1.I$1 = r6;
        r1.label = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x006c, code lost:
        if (r2.yield(r9, r1) != r0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x006e, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x006f, code lost:
        r8 = r2;
        r2 = r4;
        r4 = r6;
        r6 = r9;
        r7 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a0, code lost:
        if (r5 >= r6) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a5, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x004c, code lost:
        if (r6 > 0) goto L9;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0095 -> B:20:0x0097). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x009c -> B:22:0x00a0). Please submit an issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object $result) {
        ViewGroupKt$descendants$1 viewGroupKt$descendants$1;
        SequenceScope $this$sequence;
        ViewGroup $this$forEach$iv;
        int $i$f$forEach;
        int i;
        int childCount;
        int $i$f$forEach2;
        int $i$f$forEach3;
        ViewGroup $this$forEach$iv2;
        SequenceScope $this$sequence2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                viewGroupKt$descendants$1 = this;
                $this$sequence = (SequenceScope) viewGroupKt$descendants$1.L$0;
                $this$forEach$iv = viewGroupKt$descendants$1.$this_descendants;
                $i$f$forEach = 0;
                i = 0;
                childCount = $this$forEach$iv.getChildCount();
                break;
            case 1:
                viewGroupKt$descendants$1 = this;
                $i$f$forEach2 = 0;
                $i$f$forEach3 = viewGroupKt$descendants$1.I$1;
                i = viewGroupKt$descendants$1.I$0;
                View child = (View) viewGroupKt$descendants$1.L$2;
                ViewGroup $this$forEach$iv3 = (ViewGroup) viewGroupKt$descendants$1.L$1;
                SequenceScope $this$sequence3 = (SequenceScope) viewGroupKt$descendants$1.L$0;
                ResultKt.throwOnFailure($result);
                if (!(child instanceof ViewGroup)) {
                    childCount = $i$f$forEach3;
                    $this$forEach$iv = $this$forEach$iv3;
                    $i$f$forEach = $i$f$forEach2;
                    $this$sequence = $this$sequence3;
                    break;
                } else {
                    Sequence<View> descendants = ViewGroupKt.getDescendants((ViewGroup) child);
                    viewGroupKt$descendants$1.L$0 = $this$sequence3;
                    viewGroupKt$descendants$1.L$1 = $this$forEach$iv3;
                    viewGroupKt$descendants$1.L$2 = null;
                    viewGroupKt$descendants$1.I$0 = i;
                    viewGroupKt$descendants$1.I$1 = $i$f$forEach3;
                    viewGroupKt$descendants$1.label = 2;
                    if ($this$sequence3.yieldAll(descendants, viewGroupKt$descendants$1) != coroutine_suspended) {
                        $this$forEach$iv2 = $this$forEach$iv3;
                        $this$sequence2 = $this$sequence3;
                        $this$forEach$iv = $this$forEach$iv2;
                        childCount = $i$f$forEach3;
                        $i$f$forEach = $i$f$forEach2;
                        $this$sequence = $this$sequence2;
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                }
            case 2:
                viewGroupKt$descendants$1 = this;
                $i$f$forEach2 = 0;
                $i$f$forEach3 = viewGroupKt$descendants$1.I$1;
                i = viewGroupKt$descendants$1.I$0;
                $this$forEach$iv2 = (ViewGroup) viewGroupKt$descendants$1.L$1;
                $this$sequence2 = (SequenceScope) viewGroupKt$descendants$1.L$0;
                ResultKt.throwOnFailure($result);
                $this$forEach$iv = $this$forEach$iv2;
                childCount = $i$f$forEach3;
                $i$f$forEach = $i$f$forEach2;
                $this$sequence = $this$sequence2;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
