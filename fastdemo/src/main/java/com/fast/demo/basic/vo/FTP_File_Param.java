package com.fast.demo.basic.vo;

import lombok.Data;

/**
 * FTP文件相關參數類
 */
@Data
public class FTP_File_Param {
    //private String file_save_path;// 文件保存路徑
    //private String file_save_name;// 文件保存名稱
    private String file_origin_name;// 文件原始名稱
    private String file_url;// 文件url
}
