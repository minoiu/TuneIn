package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 03/10/2017.
 */

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * The type Simple gesture filter.
 */
public class SimpleGestureFilter extends SimpleOnGestureListener{

    /**
     * The constant SWIPE_UP.
     */
    public final static int SWIPE_UP    = 1;
    /**
     * The constant SWIPE_DOWN.
     */
    public final static int SWIPE_DOWN  = 2;
    /**
     * The constant SWIPE_LEFT.
     */
    public final static int SWIPE_LEFT  = 3;
    /**
     * The constant SWIPE_RIGHT.
     */
    public final static int SWIPE_RIGHT = 4;

    /**
     * The constant MODE_TRANSPARENT.
     */
    public final static int MODE_TRANSPARENT = 0;
    /**
     * The constant MODE_SOLID.
     */
    public final static int MODE_SOLID       = 1;
    /**
     * The constant MODE_DYNAMIC.
     */
    public final static int MODE_DYNAMIC     = 2;

    private final static int ACTION_FAKE = -13; //just an unlikely number
    private int swipe_Min_Distance = 100;
    private int swipe_Max_Distance = 350;
    private int swipe_Min_Velocity = 100;

    private int mode             = MODE_DYNAMIC;
    private boolean running      = true;
    private boolean tapIndicator = false;

    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;

    /**
     * Instantiates a new Simple gesture filter.
     *
     * @param context the context
     * @param sgl     the sgl
     */
    public SimpleGestureFilter(Activity context,SimpleGestureListener sgl) {

        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = sgl;
    }

    /**
     * On touch event.
     *
     * @param event the event
     */
    public void onTouchEvent(MotionEvent event){

        if(!this.running)
            return;

        boolean result = this.detector.onTouchEvent(event);

        if(this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {

            if(event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if(this.tapIndicator){
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }

        }
        //else just do nothing, it's Transparent
    }

    /**
     * Set mode.
     *
     * @param m the m
     */
    public void setMode(int m){
        this.mode = m;
    }

    /**
     * Get mode int.
     *
     * @return the int
     */
    public int getMode(){
        return this.mode;
    }

    /**
     * Set enabled.
     *
     * @param status the status
     */
    public void setEnabled(boolean status){
        this.running = status;
    }

    /**
     * Set swipe max distance.
     *
     * @param distance the distance
     */
    public void setSwipeMaxDistance(int distance){
        this.swipe_Max_Distance = distance;
    }

    /**
     * Set swipe min distance.
     *
     * @param distance the distance
     */
    public void setSwipeMinDistance(int distance){
        this.swipe_Min_Distance = distance;
    }

    /**
     * Set swipe min velocity.
     *
     * @param distance the distance
     */
    public void setSwipeMinVelocity(int distance){
        this.swipe_Min_Velocity = distance;
    }

    /**
     * Get swipe max distance int.
     *
     * @return the int
     */
    public int getSwipeMaxDistance(){
        return this.swipe_Max_Distance;
    }

    /**
     * Get swipe min distance int.
     *
     * @return the int
     */
    public int getSwipeMinDistance(){
        return this.swipe_Min_Distance;
    }

    /**
     * Get swipe min velocity int.
     *
     * @return the int
     */
    public int getSwipeMinVelocity(){
        return this.swipe_Min_Velocity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        if(xDistance > this.swipe_Max_Distance || yDistance > this.swipe_Max_Distance)
            return false;

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        boolean result = false;

        if(velocityX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance){
            if(e1.getX() > e2.getX()) // right to left
                this.listener.onSwipe(SWIPE_LEFT);
            else
                this.listener.onSwipe(SWIPE_RIGHT);

            result = true;
        }
        else if(velocityY > this.swipe_Min_Velocity && yDistance > this.swipe_Min_Distance){
            if(e1.getY() > e2.getY()) // bottom to up
                this.listener.onSwipe(SWIPE_UP);
            else
                this.listener.onSwipe(SWIPE_DOWN);

            result = true;
        }

        return result;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg) {
        this.listener.onDoubleTap();;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg) {

        if(this.mode == MODE_DYNAMIC){        // we owe an ACTION_UP, so we fake an
            arg.setAction(ACTION_FAKE);      //action which will be converted to an ACTION_UP later.
            this.context.dispatchTouchEvent(arg);
        }

        return false;
    }

    /**
     * The interface Simple gesture listener.
     */
    static interface SimpleGestureListener{
        /**
         * On swipe.
         *
         * @param direction the direction
         */
        void onSwipe(int direction);

        /**
         * On double tap.
         */
        void onDoubleTap();
    }

}