package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
/* loaded from: classes.dex */
public class MotionKeyAttributes extends MotionKey {
    private static final boolean DEBUG = false;
    public static final int KEY_TYPE = 1;
    static final String NAME = "KeyAttribute";
    private static final String TAG = "KeyAttributes";
    private String mTransitionEasing;
    private int mCurveFit = -1;
    private int mVisibility = 0;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mProgress = Float.NaN;

    public MotionKeyAttributes() {
        this.mType = 1;
        this.mCustom = new HashMap<>();
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> attributes) {
        if (!Float.isNaN(this.mAlpha)) {
            attributes.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            attributes.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            attributes.add("rotationZ");
        }
        if (!Float.isNaN(this.mRotationX)) {
            attributes.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            attributes.add("rotationY");
        }
        if (!Float.isNaN(this.mPivotX)) {
            attributes.add("pivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            attributes.add("pivotY");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            attributes.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            attributes.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            attributes.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            attributes.add("pathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            attributes.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            attributes.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            attributes.add("progress");
        }
        if (this.mCustom.size() > 0) {
            for (String s : this.mCustom.keySet()) {
                attributes.add("CUSTOM," + s);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0068, code lost:
        if (r1.equals("elevation") != false) goto L15;
     */
    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addValues(HashMap<String, SplineSet> splines) {
        Iterator<String> it = splines.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            SplineSet splineSet = splines.get(s);
            if (splineSet != null) {
                char c = 1;
                if (s.startsWith("CUSTOM")) {
                    String cKey = s.substring("CUSTOM".length() + 1);
                    CustomVariable cValue = this.mCustom.get(cKey);
                    if (cValue != null) {
                        ((SplineSet.CustomSpline) splineSet).setPoint(this.mFramePosition, cValue);
                    }
                } else {
                    switch (s.hashCode()) {
                        case -1249320806:
                            if (s.equals("rotationX")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1249320805:
                            if (s.equals("rotationY")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1249320804:
                            if (s.equals("rotationZ")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497657:
                            if (s.equals("translationX")) {
                                c = '\n';
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497656:
                            if (s.equals("translationY")) {
                                c = 11;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497655:
                            if (s.equals("translationZ")) {
                                c = '\f';
                                break;
                            }
                            c = 65535;
                            break;
                        case -1001078227:
                            if (s.equals("progress")) {
                                c = '\r';
                                break;
                            }
                            c = 65535;
                            break;
                        case -987906986:
                            if (s.equals("pivotX")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case -987906985:
                            if (s.equals("pivotY")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -908189618:
                            if (s.equals("scaleX")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case -908189617:
                            if (s.equals("scaleY")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case -4379043:
                            break;
                        case 92909918:
                            if (s.equals("alpha")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 803192288:
                            if (s.equals("pathRotate")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            if (!Float.isNaN(this.mAlpha)) {
                                splineSet.setPoint(this.mFramePosition, this.mAlpha);
                                break;
                            } else {
                                continue;
                            }
                        case 1:
                            if (!Float.isNaN(this.mElevation)) {
                                splineSet.setPoint(this.mFramePosition, this.mElevation);
                                break;
                            } else {
                                continue;
                            }
                        case 2:
                            if (!Float.isNaN(this.mRotation)) {
                                splineSet.setPoint(this.mFramePosition, this.mRotation);
                                break;
                            } else {
                                continue;
                            }
                        case 3:
                            if (!Float.isNaN(this.mRotationX)) {
                                splineSet.setPoint(this.mFramePosition, this.mRotationX);
                                break;
                            } else {
                                continue;
                            }
                        case 4:
                            if (!Float.isNaN(this.mRotationY)) {
                                splineSet.setPoint(this.mFramePosition, this.mRotationY);
                                break;
                            } else {
                                continue;
                            }
                        case 5:
                            if (!Float.isNaN(this.mRotationX)) {
                                splineSet.setPoint(this.mFramePosition, this.mPivotX);
                                break;
                            } else {
                                continue;
                            }
                        case 6:
                            if (!Float.isNaN(this.mRotationY)) {
                                splineSet.setPoint(this.mFramePosition, this.mPivotY);
                                break;
                            } else {
                                continue;
                            }
                        case 7:
                            if (!Float.isNaN(this.mTransitionPathRotate)) {
                                splineSet.setPoint(this.mFramePosition, this.mTransitionPathRotate);
                                break;
                            } else {
                                continue;
                            }
                        case '\b':
                            if (!Float.isNaN(this.mScaleX)) {
                                splineSet.setPoint(this.mFramePosition, this.mScaleX);
                                break;
                            } else {
                                continue;
                            }
                        case '\t':
                            if (!Float.isNaN(this.mScaleY)) {
                                splineSet.setPoint(this.mFramePosition, this.mScaleY);
                                break;
                            } else {
                                continue;
                            }
                        case '\n':
                            if (!Float.isNaN(this.mTranslationX)) {
                                splineSet.setPoint(this.mFramePosition, this.mTranslationX);
                                break;
                            } else {
                                continue;
                            }
                        case 11:
                            if (!Float.isNaN(this.mTranslationY)) {
                                splineSet.setPoint(this.mFramePosition, this.mTranslationY);
                                break;
                            } else {
                                continue;
                            }
                        case '\f':
                            if (!Float.isNaN(this.mTranslationZ)) {
                                splineSet.setPoint(this.mFramePosition, this.mTranslationZ);
                                break;
                            } else {
                                continue;
                            }
                        case '\r':
                            if (!Float.isNaN(this.mProgress)) {
                                splineSet.setPoint(this.mFramePosition, this.mProgress);
                                break;
                            } else {
                                continue;
                            }
                        default:
                            PrintStream printStream = System.err;
                            printStream.println("not supported by KeyAttributes " + s);
                            continue;
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public MotionKey clone() {
        return null;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, int value) {
        switch (type) {
            case 100:
                this.mFramePosition = value;
                return true;
            case 301:
                this.mCurveFit = value;
                return true;
            case 302:
                this.mVisibility = value;
                return true;
            default:
                if (!setValue(type, value)) {
                    return super.setValue(type, value);
                }
                return true;
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, float value) {
        switch (type) {
            case 100:
                this.mTransitionPathRotate = value;
                return true;
            case 303:
                this.mAlpha = value;
                return true;
            case 304:
                this.mTranslationX = value;
                return true;
            case 305:
                this.mTranslationY = value;
                return true;
            case 306:
                this.mTranslationZ = value;
                return true;
            case 307:
                this.mElevation = value;
                return true;
            case 308:
                this.mRotationX = value;
                return true;
            case 309:
                this.mRotationY = value;
                return true;
            case 310:
                this.mRotation = value;
                return true;
            case 311:
                this.mScaleX = value;
                return true;
            case 312:
                this.mScaleY = value;
                return true;
            case 313:
                this.mPivotX = value;
                return true;
            case 314:
                this.mPivotY = value;
                return true;
            case 315:
                this.mProgress = value;
                return true;
            case TypedValues.AttributesType.TYPE_PATH_ROTATE /* 316 */:
                this.mTransitionPathRotate = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void setInterpolation(HashMap<String, Integer> interpolation) {
        if (!Float.isNaN(this.mAlpha)) {
            interpolation.put("alpha", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mElevation)) {
            interpolation.put("elevation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotation)) {
            interpolation.put("rotationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationX)) {
            interpolation.put("rotationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationY)) {
            interpolation.put("rotationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotX)) {
            interpolation.put("pivotX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotY)) {
            interpolation.put("pivotY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationX)) {
            interpolation.put("translationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationY)) {
            interpolation.put("translationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            interpolation.put("translationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            interpolation.put("pathRotate", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            interpolation.put("scaleX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleY)) {
            interpolation.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            interpolation.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustom.size() > 0) {
            for (String s : this.mCustom.keySet()) {
                interpolation.put("CUSTOM," + s, Integer.valueOf(this.mCurveFit));
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, String value) {
        switch (type) {
            case 101:
                this.mTargetString = value;
                return true;
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
                this.mTransitionEasing = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String name) {
        return TypedValues.AttributesType.CC.getId(name);
    }

    public int getCurveFit() {
        return this.mCurveFit;
    }

    public void printAttributes() {
        HashSet<String> nameSet = new HashSet<>();
        getAttributeNames(nameSet);
        PrintStream printStream = System.out;
        printStream.println(" ------------- " + this.mFramePosition + " -------------");
        String[] names = (String[]) nameSet.toArray(new String[0]);
        for (int i = 0; i < names.length; i++) {
            int id = TypedValues.AttributesType.CC.getId(names[i]);
            PrintStream printStream2 = System.out;
            printStream2.println(names[i] + ":" + getFloatValue(id));
        }
    }

    private float getFloatValue(int id) {
        switch (id) {
            case 100:
                return this.mFramePosition;
            case 303:
                return this.mAlpha;
            case 304:
                return this.mTranslationX;
            case 305:
                return this.mTranslationY;
            case 306:
                return this.mTranslationZ;
            case 307:
                return this.mElevation;
            case 308:
                return this.mRotationX;
            case 309:
                return this.mRotationY;
            case 310:
                return this.mRotation;
            case 311:
                return this.mScaleX;
            case 312:
                return this.mScaleY;
            case 313:
                return this.mPivotX;
            case 314:
                return this.mPivotY;
            case 315:
                return this.mProgress;
            case TypedValues.AttributesType.TYPE_PATH_ROTATE /* 316 */:
                return this.mTransitionPathRotate;
            default:
                return Float.NaN;
        }
    }
}
