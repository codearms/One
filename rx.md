// ---------------------------------------------------------------------------------------------------------------
        // timer：创建型操作符,用于延时执行任务。
        // interval：创建型操作符,用于周期执行任务。
        // delay：辅助型操作,用于延时传递数据。

        // 1s 之后执行任务
        /*tvJump.setVisibility(View.GONE);
        Observable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            Log.e("info", aLong + "");
            tvJump.setVisibility(View.VISIBLE);
        });*/

        // 第一次任务执行前有 1s 的间隔,每隔 1s 执行一次任务,只执行三次
        /*tvJump.setText("跳过 " + count);
        Observable.interval(1000, TimeUnit.MILLISECONDS).take(3).subscribe(aLong -> {
            Log.e("info", aLong + "");
            tvJump.setText("跳过 " + (2 - aLong));
        });*/

        // 立即执行第一次任务,每隔 1s 执行一次任务,只执行三次
        /*tvJump.setText("跳过 " + count);
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS).take(3).subscribe(aLong -> {
            Log.e("info", aLong + "");
            tvJump.setText("跳过 " + (2 - aLong));
        });*/
        // ---------------------------------------------------------------------------------------------------------------