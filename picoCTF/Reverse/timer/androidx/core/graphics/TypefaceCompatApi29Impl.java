package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromInputStream(Context context, InputStream is) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0067, code lost:
        if (r0 != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0069, code lost:
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006e, code lost:
        if ((r15 & 1) == 0) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0070, code lost:
        r5 = androidx.constraintlayout.core.motion.utils.TypedValues.TransitionType.TYPE_DURATION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0073, code lost:
        r5 = 400;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0077, code lost:
        if ((r15 & 2) == 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0079, code lost:
        r4 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007c, code lost:
        r3 = new android.graphics.fonts.FontStyle(r5, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0090, code lost:
        return new android.graphics.Typeface.CustomFallbackBuilder(r0.build()).setStyle(r3).build();
     */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        FontFamily.Builder familyBuilder = null;
        ContentResolver resolver = context.getContentResolver();
        try {
            int length = fonts.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = 1;
                if (i2 >= length) {
                    break;
                }
                FontsContractCompat.FontInfo font = fonts[i2];
                try {
                    ParcelFileDescriptor pfd = resolver.openFileDescriptor(font.getUri(), "r", cancellationSignal);
                    if (pfd != null) {
                        try {
                            Font.Builder weight = new Font.Builder(pfd).setWeight(font.getWeight());
                            if (!font.isItalic()) {
                                i3 = 0;
                            }
                            Font platformFont = weight.setSlant(i3).setTtcIndex(font.getTtcIndex()).build();
                            if (familyBuilder == null) {
                                familyBuilder = new FontFamily.Builder(platformFont);
                            } else {
                                familyBuilder.addFont(platformFont);
                            }
                            if (pfd != null) {
                                pfd.close();
                            }
                        } catch (Throwable th) {
                            if (pfd != null) {
                                try {
                                    pfd.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                            break;
                        }
                    } else if (pfd != null) {
                        pfd.close();
                    }
                } catch (IOException e) {
                }
                i2++;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry familyEntry, Resources resources, int style) {
        FontFamily.Builder familyBuilder = null;
        try {
            FontResourcesParserCompat.FontFileResourceEntry[] entries = familyEntry.getEntries();
            int length = entries.length;
            int i = 0;
            while (true) {
                int i2 = 1;
                if (i >= length) {
                    break;
                }
                FontResourcesParserCompat.FontFileResourceEntry entry = entries[i];
                try {
                    Font.Builder weight = new Font.Builder(resources, entry.getResourceId()).setWeight(entry.getWeight());
                    if (!entry.isItalic()) {
                        i2 = 0;
                    }
                    Font platformFont = weight.setSlant(i2).setTtcIndex(entry.getTtcIndex()).setFontVariationSettings(entry.getVariationSettings()).build();
                    if (familyBuilder == null) {
                        familyBuilder = new FontFamily.Builder(platformFont);
                    } else {
                        familyBuilder.addFont(platformFont);
                    }
                } catch (IOException e) {
                }
                i++;
            }
            if (familyBuilder == null) {
                return null;
            }
            FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? TypedValues.TransitionType.TYPE_DURATION : 400, (style & 2) != 0 ? 1 : 0);
            return new Typeface.CustomFallbackBuilder(familyBuilder.build()).setStyle(defaultStyle).build();
        } catch (Exception e2) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        try {
            Font font = new Font.Builder(resources, id).build();
            FontFamily family = new FontFamily.Builder(font).build();
            return new Typeface.CustomFallbackBuilder(family).setStyle(font.getStyle()).build();
        } catch (Exception e) {
            return null;
        }
    }
}
