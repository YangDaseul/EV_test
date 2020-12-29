package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.SOS_1003;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.ui.common.activity.HtmlActivity;

import androidx.lifecycle.ViewModelProvider;

public class ServiceSOSPayInfoActivity extends HtmlActivity {

    private SOSViewModel sosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        sosViewModel.reqSOS1003(new SOS_1003.Request(APPIAInfo.SM_EMGC01_P04.getId()));
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {

        sosViewModel.getRES_SOS_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null&&!TextUtils.isEmpty(result.data.getCont())) {

                        String test1 = "<!DOCTYPE html>\n" +
                                "<html lang=ko>\n" +
                                "<head><title>추가 비용 안내 팝업</title>\n" +
                                "    <meta charset=utf-8/>\n" +
                                "    <meta name=viewport content=width=device-width, initial-scale=1.0, maximum-scale=1.0,\n" +
                                "          minimum-scale=1.0, user-scalable=no, viewport-fit=cover/>\n" +
                                "    <style>\n" +
                                "        html, body, div, span, object, iframe,h1, h2, h3, h4, h5, h6, p, blockquote, pre,abbr, address, cite, code,del, dfn, em, img, ins, kbd, q, samp,small, strong, sub, sup, var,b, i, dl, dt, dd, ol, ul, li,fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td,article, aside, canvas, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary,time, mark, audio, video, a {margin:0;padding:0;border:0;outline:0;box-sizing:border-box}section, header, footer{position:relative}fieldset,img {border:0 none}dl,ul,ol,menu,li {list-style:none}blockquote, q {quotes:none}blockquote:before, blockquote:after,q:before, q:after {content:'';content:none}input,select,textarea,button {vertical-align:middle;box-sizing:border-box}button {border:0 none;background-color:transparent;cursor:pointer}table {border-collapse:collapse;border-spacing:0;width:100%}body {background:#fff;min-width:320px;-webkit-text-size-adjust:none;line-height:1.2;letter-spacing:-1px}input[type='text'],input[type='password'],input[type='number'], input[type='date'],input[type='submit'],input[type='search'] {-webkit-appearance:none; border-radius:0}input:checked[type='checkbox'] {background-color:#666; -webkit-appearance:checkbox}button,input[type='button'],input[type='submit'],input[type='reset'],input[type='file'] {-webkit-appearance:button; border-radius:0}input[type='search']::-webkit-search-cancel-button {-webkit-appearance:none}h1, h2, h3, h4, h5, h6, th{font-weight:normal}a {text-decoration:none}a:link, a:active, a:hover, a:visited {text-decoration:none;color:inherit}address,caption,cite,code,dfn,em,var{font-style:normal;font-weight:normal}caption{height:0; font-size:0; line-height:0; overflow:hidden}textarea,input,select{letter-spacing:-1px;padding:0.4em}select{-webkit-appearance:none}@font-face {    font-family: 'GH Regular';src:    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansHeadKRRegular.eot) format('embedded-opentype'),    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansHeadKRRegular.woff) format('woff');}@font-face {    font-family: 'GH Light';src:    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansHeadKRLight.eot) format('embedded-opentype'),    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansHeadKRLight.woff) format('woff');}@font-face {    font-family: 'GT Regular';src:    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRRegular.eot) format('embedded-opentype'),    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRRegular.woff) format('woff');}@font-face {    font-family: 'GT Bold';src:    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRBold.eot) format('embedded-opentype'),    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRBold.woff) format('woff');}@font-face {    font-family: 'GT Medium';src:    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRMedium.eot) format('embedded-opentype'),    url(https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/assets/font/GenesisSansTextKRMedium.woff) format('woff');}/* common */html, body{width:100%;height:100%;overflow-y:auto;overflow-x:hidden}b{font-family:GT Bold;font-weight:normal}.contents{background:#fff;min-height:100%;font-family:GT Regular}.contents.bg{background:#f4f4f4}.box{padding:20px}/* box */.box.terms{padding:30px 20px;font-size:14px;line-height:1.5}.box.terms .t1{font:20px/1.5 GH Regular}.box.terms .t2{font:16px/1.5 GT Bold}.box.terms .t3{font:14px/1.5 GT Bold}.box.terms p{font-size:inherit}/*** template ***/.ib{display:block;width:100%}.taL{text-align:left !important}.taR{text-align:right !important}.taC{text-align:center !important}.mgT5{margin-top:5px !important}.mgT10{margin-top:10px !important}.mgT15{margin-top:15px !important}.mgT20{margin-top:20px !important}.mgT30{margin-top:30px !important}.mgT40{margin-top:40px !important}.pdT10{padding-top:10px !important}/* title */.m-tit{display:block;font:18px GH Regular;color:#000}.p-tit{display:block;font:22px GH Light}/* font */.fT1{color:#525252;font-size:13px;font-family:GH Light}.fC5{color:#525252}/* bullet */.bltLn > li{position:relative;padding-left:17px;font-size:inherit;color:inherit}.bltLn.ty1 > li{padding-left:11px}.bltLn > li span.blt{position:absolute;left:0;content:''}.bltL0 > li{position:relative;padding-left:8px;font-size:inherit;color:inherit}.bltL0 > li:before{position:absolute;left:0;content:'-'}.bltL1 > li{position:relative;padding-left:8px;font-size:inherit;color:inherit}.bltL1 > li:before{position:absolute;top:8px;left:0;display:block;width:2px;height:2px;background:#aaabaf;content:''}.bltL1.ty1 > li:before{background:#000;top:9px}.bltn{position:relative;padding-left:17px;font-size:inherit;color:inherit}.bltn.ty1{padding-left:11px}.bltn span.blt{position:absolute;left:0;content:''}.blt0{position:relative;padding-left:8px;font-size:inherit;color:inherit}.blt0:before{position:absolute;left:0;content:'-'}.blt1{position:relative;padding-left:8px;font-size:inherit;color:inherit}.blt1:before{position:absolute;top:8px;left:0;display:block;width:2px;height:2px;background:#aaabaf;content:''}.blt1.ty1:before{background:#000;top:9px}.bltLn > li .tbl-wrap{margin-left:-17px}/* table */.tbl-wrap{width:100%;overflow-x:auto}.tbl-ty1{border-top:1px solid #525252;border-bottom:1px solid #e2e2e2}.tbl-ty1 th{font:13px GH Light;padding:15px}.tbl-ty1 td{font-size:16px;padding:15px;border-top:1px solid #f4f4f4}.tbl-ty1 tbody th{border-top:1px solid #f4f4f4}.tbl-terms{width:100%;table-layout:fixed;border:1px solid #e2e2e2;font-size:13px}.tbl-terms th{padding:9px 0 8px;text-align:center;background:#4a4a4a;border:1px solid #e2e2e2;color:#fff}.tbl-terms td{padding:10px 12px 9px;color:#4a4a4a;text-align:left;vertical-align:middle;border:1px solid #e2e2e2;background:#fff}.tbl-terms.taC td{text-align:center}/* list */.bx-li1:after{display:block;clear:both;content:''}.bx-li1 li{float:left;width:calc(50% - 3px);margin-left:6px;padding:20px 15px;background:#efefef}.bx-li1 li:nth-child(2n-1){margin-left:0}.bx-li1 li:nth-child(n+3){margin-top:6px}.bx-li1 li .dt{display:block;font:13px GH Light;color:#525252}.bx-li1 li .dd{display:block;font-size:16px;color:#141414;margin-top:8px}.arr-li1 li{margin-top:4px}.arr-li1 li:first-child{margin-top:0}.arr-li1 li .btn-detail{position:relative;display:block;width:100%;height:60px;line-height:58px;border:1px solid #e2e2e2;font-size:16px;padding:0 15px}.arr-li1 li .btn-detail:after{position:absolute;right:15px;top:50%;margin-top:-8px;display:block;width:8px;height:8px;border:solid #222;border-width: 0px 1px 1px 0px;-webkit-transform:rotate(45deg);transform:rotate(45deg);content:''}.arr-li1 li .btn-detail.on:after{transform:rotate(225deg);margin-top:-3px}.arr-li1 li .detail{display:none;background:#f9f9f9;border:solid #e2e2e2;border-width:0 1px 1px 1px;padding:20px 15px;color:#525252}.arr-li1 li .detail.on{display:block}.arr-li1 li .detail dt{font:14px GT Medium;margin-top:20px}.arr-li1 li .detail dt:first-child{margin-top:0}.arr-li1 li .detail dd{font-size:14px;margin-top:10px;line-height:1.4}.list-ty1 li{position:relative;padding:20px 0;border-top:1px solid #f4f4f4}.list-ty1 li:first-child{border-top:0}.list-ty1 .dt{display:block;font:14px GT Medium;color:#525252}.list-ty1 .dd{display:block;font-size:14px;color:#525252;margin-top:10px}.list-ty1.bullet{padding-left:20px}.list-ty1.bullet li{font-size:14px;color:#000;line-height:1.4}.list-ty1.bullet li .blt{position:absolute;left:-20px;color:#aaabaf}.list-ty1.bullet li .vouchers h2{font:14px GH Regular;color:#000}.list-ty1.bullet li .vouchers .bx{padding:20px 15px;background:#f4f4f4;margin-top:4px}.list-ty1.bullet li .vouchers .bx h3{font-size:13px;color:#000}.list-ty1.bullet li .vouchers .bx p{font-size:15px;color:#000;margin-top:10px}.list-ty1.bullet li .vouchers .bx .blt1{font-size:14px;color:#aaabaf;margin-top:10px}.dl-ty1 dt{font:14px GT Medium;color:#525252;margin-top:20px}.dl-ty1 dt:first-child{margin-top:0}.dl-ty1 dd{font-size:14px;color:#525252;margin-top:5px}.dl-ty1 dd .bltL1 li{color:#aaabaf}.dl-ty1 dd .tr{display:table;width:100%}.dl-ty1 dd .tr li{display:table-cell;white-space:normal}.dl-ty1 dd .tr li:first-child{width:61px}.dl-ty1 dd .tr li.ty1{width:88px}\n" +
                                "    </style>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<section class=contents>\n" +
                                "    <div class=box pdT10><h1 class=p-tit>추가 비용 안내</h1>\n" +
                                "        <table class=tbl-ty1 mgT20>\n" +
                                "            <thead>\n" +
                                "            <tr>\n" +
                                "                <th scope=col class=taL>차종<br/>(무게)</th>\n" +
                                "                <th scope=col>주간<br/>(08:00 - 18:00)</th>\n" +
                                "                <th scope=col>야간<br/>(18:01 - 07:59)</th>\n" +
                                "            </tr>\n" +
                                "            </thead>\n" +
                                "            <tbody>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>승용, RV,<br/>포터, 스타렉스</th>\n" +
                                "                <td class=taR>18,000</td>\n" +
                                "                <td class=taR>22,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>2T - 4.5T 미만</th>\n" +
                                "                <td class=taR>27,000</td>\n" +
                                "                <td class=taR>32,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>4.5T - 8T 미만</th>\n" +
                                "                <td class=taR>32,000</td>\n" +
                                "                <td class=taR>37,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>8T 이상</th>\n" +
                                "                <td class=taR>37,000</td>\n" +
                                "                <td class=taR>42,000</td>\n" +
                                "            </tr>\n" +
                                "            </tbody>\n" +
                                "        </table>\n" +
                                "        <p class=fT1 taR mgT10>(단위 : 원, VAT포함)</p></div><!--// .box --></section>\n" +
                                "<!--// .contents --></body>\n" +
                                "</html>";

                        String test2 = "<!DOCTYPE html>\n" +
                                "<html lang=ko>\n" +
                                "<head><title>추가 비용 안내 팝업</title>\n" +
                                "    <meta charset=utf-8/>\n" +
                                "    <meta name=viewport content=width=device-width, initial-scale=1.0, maximum-scale=1.0,\n" +
                                "          minimum-scale=1.0, user-scalable=no, viewport-fit=cover/>\n" +
                                "    <style>\n" +
                                "    </style>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<section class=contents>\n" +
                                "    <div class=box pdT10><h1 class=p-tit>추가 비용 안내</h1>\n" +
                                "        <table class=tbl-ty1 mgT20>\n" +
                                "            <thead>\n" +
                                "            <tr>\n" +
                                "                <th scope=col class=taL>차종<br/>(무게)</th>\n" +
                                "                <th scope=col>주간<br/>(08:00 - 18:00)</th>\n" +
                                "                <th scope=col>야간<br/>(18:01 - 07:59)</th>\n" +
                                "            </tr>\n" +
                                "            </thead>\n" +
                                "            <tbody>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>승용, RV,<br/>포터, 스타렉스</th>\n" +
                                "                <td class=taR>18,000</td>\n" +
                                "                <td class=taR>22,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>2T - 4.5T 미만</th>\n" +
                                "                <td class=taR>27,000</td>\n" +
                                "                <td class=taR>32,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>4.5T - 8T 미만</th>\n" +
                                "                <td class=taR>32,000</td>\n" +
                                "                <td class=taR>37,000</td>\n" +
                                "            </tr>\n" +
                                "            <tr>\n" +
                                "                <th scope=row class=taL>8T 이상</th>\n" +
                                "                <td class=taR>37,000</td>\n" +
                                "                <td class=taR>42,000</td>\n" +
                                "            </tr>\n" +
                                "            </tbody>\n" +
                                "        </table>\n" +
                                "        <p class=fT1 taR mgT10>(단위 : 원, VAT포함)</p></div><!--// .box --></section>\n" +
                                "<!--// .contents --></body>\n" +
                                "</html>";

                        loadTerms(test1);
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    private void initView() {

    }


    @Override
    public void onClickCommon(View v) {

    }

}
