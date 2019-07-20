package com.lingsi.unp.controller.apply;

        import com.lingsi.unp.service.notarial.myenum.MyEnum;
        import com.lingsi.unp.utils.io.file.FilePath;
        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiOperation;
        import io.swagger.annotations.ApiParam;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletResponse;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.OutputStream;

@Api(value="公证申请服务",tags = {"公证申请服务"})
@RestController
@RequestMapping(value = "/notarial/view")
public class ViewNPDF_Controller {

    @RequestMapping(value = "/{token}/{fileId}",method= RequestMethod.GET,produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value="审批 -- 申请表")
    public void applyTable(@ApiParam(value = "caseId", example = "10.pdf") @PathVariable String fileId,
                           @ApiParam(value = "token", example = "asldajlkxS002") @PathVariable String token,HttpServletResponse response) {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            System.out.println("param fileId = " + fileId);
            String path = FilePath.getTemplateFilePath(MyEnum.NotarialPdfSavePath)+fileId;

            inputStream = new FileInputStream(new File(path));

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024 * 1024];
            int len;

            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.getMessage();
                }

                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

}
