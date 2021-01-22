package com.didi.carmate.dreambox.core.v4.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;

import com.didi.carmate.dreambox.core.v4.render.view.DBFrameLayoutView;
import com.didi.carmate.dreambox.core.v4.render.view.DBLinearLayoutView;
import com.didi.carmate.dreambox.core.v4.render.view.DBYogaLayoutView;
import com.didi.carmate.dreambox.core.v4.utils.DBScreenUtils;
import com.didi.carmate.dreambox.core.v4.utils.DBUtils;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaPositionType;

import java.util.HashMap;
import java.util.Map;

import static com.didi.carmate.dreambox.core.v4.base.DBConstants.ALIGN_SELF_BASELINE;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.ALIGN_SELF_CENTER;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.ALIGN_SELF_END;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.ALIGN_SELF_START;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.ALIGN_SELF_STRETCH;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_BOTTOM;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_LEFT;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_RIGHT;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_TOP;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_TYPE_ABSOLUTE;
import static com.didi.carmate.dreambox.core.v4.base.DBConstants.POSITION_TYPE_RELATIVE;

/**
 * author: chenjing
 * date: 2020/11/10
 */
public abstract class DBAbsView<V extends View> extends DBBindView {
    private final Map<String, Integer> mapGravity = new HashMap<>();

    protected SparseArray<View> mViews = new SparseArray<>(); // 待优化，暂时用下标记住和数据源的映射关系
    protected View mNativeView;
    // 通用属性
    protected int id = DBConstants.DEFAULT_ID_VIEW;
    protected int width;
    protected int height;
    protected String background; // 背景图片
    protected int radius; // 背景圆角半径
    protected int radiusLT; // 左上角圆角半径
    protected int radiusRT; // 右上角圆角半径
    protected int radiusRB; // 右下角圆角半径
    protected int radiusLB; // 左下角圆角半径
    protected String backgroundColor; // 背景颜色
    protected int margin;
    protected int marginTop;
    protected int marginBottom;
    protected int marginLeft;
    protected int marginRight;
    protected int padding;
    protected int paddingTop;
    protected int paddingBottom;
    protected int paddingLeft;
    protected int paddingRight;

    protected int layoutGravity;
    protected int gravity;

    // YogaLayout属性
    protected float flexGrow;
    protected float flexShrink;
    protected float flexBasis;
    protected String alignSelf;

    protected String positionType;
    protected String position;
    protected float positionDistance;
    protected float positionDistancePercent;

    protected float widthPercent;
    protected float heightPercent;
    protected float flexBasisPercent;

    protected DBAbsView(DBContext dbContext) {
        super(dbContext);

        mapGravity.put(DBConstants.STYLE_GRAVITY_LEFT, Gravity.START);
        mapGravity.put(DBConstants.STYLE_GRAVITY_RIGHT, Gravity.END);
        mapGravity.put(DBConstants.STYLE_GRAVITY_TOP, Gravity.TOP);
        mapGravity.put(DBConstants.STYLE_GRAVITY_BOTTOM, Gravity.BOTTOM);
        mapGravity.put(DBConstants.STYLE_GRAVITY_CENTER, Gravity.CENTER);
        mapGravity.put(DBConstants.STYLE_GRAVITY_CENTER_VERTICAL, Gravity.CENTER_VERTICAL);
        mapGravity.put(DBConstants.STYLE_GRAVITY_CENTER_HORIZONTAL, Gravity.CENTER_HORIZONTAL);
    }

    protected abstract View onCreateView();

    protected V onGetParentNativeView() {
        return null;
    }

    public int getId() {
        return id;
    }

    public View getNativeView() {
        return mNativeView;
    }

    @CallSuper
    protected void onParseLayoutAttr(Map<String, String> attrs) {
        // 边距
        margin = DBScreenUtils.processSize(mDBContext, attrs.get("margin"), DBConstants.DEFAULT_SIZE_EDGE);
        marginLeft = DBScreenUtils.processSize(mDBContext, attrs.get("marginLeft"), DBConstants.DEFAULT_SIZE_EDGE);
        marginTop = DBScreenUtils.processSize(mDBContext, attrs.get("marginTop"), DBConstants.DEFAULT_SIZE_EDGE);
        marginRight = DBScreenUtils.processSize(mDBContext, attrs.get("marginRight"), DBConstants.DEFAULT_SIZE_EDGE);
        marginBottom = DBScreenUtils.processSize(mDBContext, attrs.get("marginBottom"), DBConstants.DEFAULT_SIZE_EDGE);
        // padding
        padding = DBScreenUtils.processSize(mDBContext, attrs.get("padding"), DBConstants.DEFAULT_SIZE_EDGE);
        paddingLeft = DBScreenUtils.processSize(mDBContext, attrs.get("paddingLeft"), DBConstants.DEFAULT_SIZE_EDGE);
        paddingTop = DBScreenUtils.processSize(mDBContext, attrs.get("paddingTop"), DBConstants.DEFAULT_SIZE_EDGE);
        paddingRight = DBScreenUtils.processSize(mDBContext, attrs.get("paddingRight"), DBConstants.DEFAULT_SIZE_EDGE);
        paddingBottom = DBScreenUtils.processSize(mDBContext, attrs.get("paddingBottom"), DBConstants.DEFAULT_SIZE_EDGE);

        // 宽高
        width = DBConstants.DEFAULT_SIZE_WIDTH;
        String w = attrs.get("width");
        if (null != w) {
            if (w.endsWith("%")) {
                w = w.substring(0, w.length() - 1);
                if (DBUtils.isNumeric(w)) {
                    widthPercent = Float.parseFloat(w);
                }
            } else {
                width = DBScreenUtils.processSize(mDBContext, w, DBConstants.DEFAULT_SIZE_WIDTH);
            }
        }
        height = DBConstants.DEFAULT_SIZE_HEIGHT;
        String h = attrs.get("height");
        if (null != h) {
            if (h.endsWith("%")) {
                h = h.substring(0, h.length() - 1);
                if (DBUtils.isNumeric(h)) {
                    heightPercent = Float.parseFloat(h);
                }
            } else {
                height = DBScreenUtils.processSize(mDBContext, h, DBConstants.DEFAULT_SIZE_HEIGHT);
            }
        }

        // 圆角
        radius = DBScreenUtils.processSize(mDBContext, attrs.get("radius"), 0);
        radiusLT = DBScreenUtils.processSize(mDBContext, attrs.get("radiusLT"), 0);
        radiusRT = DBScreenUtils.processSize(mDBContext, attrs.get("radiusRT"), 0);
        radiusRB = DBScreenUtils.processSize(mDBContext, attrs.get("radiusRB"), 0);
        radiusLB = DBScreenUtils.processSize(mDBContext, attrs.get("radiusLB"), 0);

        // YogaLayout属性
        String fg = attrs.get("flex-grow");
        if (DBUtils.isNumeric(fg)) {
            flexGrow = Float.parseFloat(fg);
        }
        String fs = attrs.get("flex-shrink");
        if (DBUtils.isNumeric(fs)) {
            flexShrink = Float.parseFloat(fs);
        }
        String fb = attrs.get("flex-basis");
        if (null != fb) {
            if (fb.endsWith("%")) {
                fb = fb.substring(0, fb.length() - 1);
                if (DBUtils.isNumeric(fb)) {
                    flexBasisPercent = Float.parseFloat(fb);
                }
            } else {
                flexBasis = DBScreenUtils.processSize(mDBContext, fb, 0);
            }
        }
        alignSelf = attrs.get("align-self");
        positionType = attrs.get("positionType");
        position = attrs.get("position");
        String pd = attrs.get("positionDistance");
        if (null != pd) {
            if (pd.endsWith("%")) {
                pd = pd.substring(0, pd.length() - 1);
                if (DBUtils.isNumeric(pd)) {
                    positionDistancePercent = Float.parseFloat(pd);
                }
            } else {
                positionDistance = DBScreenUtils.processSize(mDBContext, pd, 0);
            }
        }
    }

    @CallSuper
    protected void onAttributesBind(final Map<String, String> attrs) {
        // background
        background = getString(attrs.get("background"));
        if (!DBUtils.isEmpty(background)) {
            Context context = mDBContext.getContext();
            int resId = context.getResources().getIdentifier(background, "drawable", context.getPackageName());
            mNativeView.setBackgroundResource(resId);
        }
        // backgroundColor
        backgroundColor = getString(attrs.get("backgroundColor"));
        if (DBUtils.isColor(backgroundColor)) {
            // 外矩形 左上、右上、右下、左下的圆角半径
            float[] outerRadiusAll = {radius, radius, radius, radius, radius, radius, radius, radius};
            float[] outerRadius = {radiusLT, radiusLT, radiusRT, radiusRT, radiusRB, radiusRB, radiusLB, radiusLB};
            // 背景drawable
            RoundRectShape bgShape = new RoundRectShape(radius != 0 ? outerRadiusAll : outerRadius, null, null);
            ShapeDrawable bgDrawable = new ShapeDrawable(bgShape);
            bgDrawable.getPaint().setColor(Color.parseColor(backgroundColor));
            bgDrawable.getPaint().setAntiAlias(true);
            bgDrawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);//描边
            mNativeView.setBackground(bgDrawable);
        }
        // layoutGravity
        String rawLayoutGravity = getString(attrs.get("layoutGravity"));
        if (!DBUtils.isEmpty(rawLayoutGravity)) {
            layoutGravity = convertGravity(rawLayoutGravity);
        }
        // layoutGravity
        String rawGravity = getString(attrs.get("gravity"));
        if (!DBUtils.isEmpty(rawGravity)) {
            gravity = convertGravity(rawGravity);
        }
    }

    @CallSuper
    protected void onViewAdded(ViewGroup parentView) {
        if (parentView instanceof DBYogaLayoutView) {
            // YogaLayout
            bindAttributesInYogaLayout(parentView);
        } else if (parentView instanceof DBLinearLayoutView) {
            // LinearLayout
            bindAttributesInLinearLayout(parentView);
        } else if (parentView instanceof DBFrameLayoutView) {
            // FrameLayout
            bindAttributesInFrameLayout(parentView);
        }
    }

    private int convertGravity(String gravity) {
        if (null == gravity) {
            return 0;
        }
        String[] gravityArr = gravity.split("\\|");
        int iGravity = 0;
        for (String strGravity : gravityArr) {
            Integer tmp = mapGravity.get(strGravity);
            if (null != tmp) {
                iGravity |= tmp;
            }
        }
        return iGravity;
    }

    private void bindAttributesInLinearLayout(ViewGroup parentView) {

    }

    private void bindAttributesInFrameLayout(ViewGroup parentView) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mNativeView.getLayoutParams();
        layoutParams.gravity = layoutGravity;
    }

    private void bindAttributesInYogaLayout(ViewGroup parentView) {
        YogaNode node = ((DBYogaLayoutView) parentView).getYogaNodeForView(mNativeView);
        if (margin > 0) {
            node.setMargin(YogaEdge.LEFT, margin);
            node.setMargin(YogaEdge.TOP, margin);
            node.setMargin(YogaEdge.RIGHT, margin);
            node.setMargin(YogaEdge.BOTTOM, margin);
        } else {
            if (marginLeft != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setMargin(YogaEdge.LEFT, marginLeft);
            }
            if (marginTop != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setMargin(YogaEdge.TOP, marginTop);
            }
            if (marginRight != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setMargin(YogaEdge.RIGHT, marginRight);
            }
            if (marginBottom != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setMargin(YogaEdge.BOTTOM, marginBottom);
            }
        }

        if (padding > 0) {
            node.setPadding(YogaEdge.LEFT, padding);
            node.setPadding(YogaEdge.TOP, padding);
            node.setPadding(YogaEdge.RIGHT, padding);
            node.setPadding(YogaEdge.BOTTOM, padding);
        } else {
            if (paddingLeft != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setPadding(YogaEdge.LEFT, paddingLeft);
            }
            if (paddingTop != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setPadding(YogaEdge.TOP, paddingTop);
            }
            if (paddingRight != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setPadding(YogaEdge.RIGHT, paddingRight);
            }
            if (paddingBottom != DBConstants.DEFAULT_SIZE_EDGE) {
                node.setPadding(YogaEdge.BOTTOM, paddingBottom);
            }
        }

        if (flexGrow != 0) {
            node.setFlexGrow(flexGrow);
        }
        if (flexShrink != 0) {
            node.setFlexShrink(flexShrink);
        }
        if (flexBasis != 0) {
            node.setFlexBasis(flexBasis);
        }
        if (flexBasisPercent != 0) {
            node.setFlexBasisPercent(flexBasisPercent);
        }

        if (width > 0) {
            node.setWidth(width);
        } else if (widthPercent != 0) {
            node.setWidthPercent(widthPercent);
        }
        if (height > 0) {
            node.setHeight(height);
        } else if (heightPercent != 0) {
            node.setHeightPercent(heightPercent);
        }

        if (null != alignSelf) {
            switch (alignSelf) {
                case ALIGN_SELF_START:
                    node.setAlignSelf(YogaAlign.FLEX_START);
                    break;
                case ALIGN_SELF_END:
                    node.setAlignSelf(YogaAlign.FLEX_END);
                    break;
                case ALIGN_SELF_CENTER:
                    node.setAlignSelf(YogaAlign.CENTER);
                    break;
                case ALIGN_SELF_STRETCH:
                    node.setAlignSelf(YogaAlign.STRETCH);
                    break;
                case ALIGN_SELF_BASELINE:
                    node.setAlignSelf(YogaAlign.BASELINE);
                    break;
            }
        }
        if (null != positionType) {
            switch (positionType) {
                case POSITION_TYPE_RELATIVE:
                    node.setPositionType(YogaPositionType.RELATIVE);
                    break;
                case POSITION_TYPE_ABSOLUTE:
                    node.setPositionType(YogaPositionType.ABSOLUTE);
                    break;
            }
        }
        if (null != position) {
            switch (position) {
                case POSITION_LEFT:
                    if (positionDistancePercent > 0) {
                        node.setPositionPercent(YogaEdge.LEFT, positionDistancePercent);
                    } else {
                        node.setPosition(YogaEdge.LEFT, positionDistance);
                    }
                    break;
                case POSITION_TOP:
                    if (positionDistancePercent > 0) {
                        node.setPositionPercent(YogaEdge.TOP, positionDistancePercent);
                    } else {
                        node.setPosition(YogaEdge.TOP, positionDistance);
                    }
                    break;
                case POSITION_RIGHT:
                    if (positionDistancePercent > 0) {
                        node.setPositionPercent(YogaEdge.RIGHT, positionDistancePercent);
                    } else {
                        node.setPosition(YogaEdge.RIGHT, positionDistance);
                    }
                    break;
                case POSITION_BOTTOM:
                    if (positionDistancePercent > 0) {
                        node.setPositionPercent(YogaEdge.BOTTOM, positionDistancePercent);
                    } else {
                        node.setPosition(YogaEdge.BOTTOM, positionDistance);
                    }
                    break;
            }
        }
    }
}
