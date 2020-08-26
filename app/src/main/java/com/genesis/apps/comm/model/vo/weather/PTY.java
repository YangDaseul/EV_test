package com.genesis.apps.comm.model.vo.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 강수형태(PTY) : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
 * @author hjpark
 */
@EqualsAndHashCode(callSuper = true)
public @Data
class PTY extends BaseWeather {


}
