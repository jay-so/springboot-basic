package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.controller.VoucherController;
import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.enums.Command;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.view.Console;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleApplication implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;

    @Override
    public void run(String... args) throws Exception {
        console.consoleMenu();

        while (true) {
            try {
                String command = console.inputCommand();
                Command inputCommand = Command.of(command);

                switch (inputCommand) {
                    case CREATE -> createVoucher();
                    case LIST -> getVoucherList();
                    case EXIT -> {
                        console.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.error("명령어가 잘못 입력되었습니다. ", e);
            } catch (Exception e) {
                log.error("프로그램에서 오류가 발생하였습니다.", e);
            }
        }
    }

    private void createVoucher() {
        String voucherTypeInput = console.inputVoucherType();
        VoucherType voucherType = VoucherType.of(voucherTypeInput);

        long voucherDiscount = console.inputVoucherDiscount();

        voucherController.createVoucher(voucherType, voucherDiscount);
        console.printMessage("바우처가 생성되었습니다!");
    }

    private void getVoucherList() {
        Map<UUID, Voucher> voucherMap = voucherController.printVoucherList();
        console.printlnVoucherList(voucherMap);
    }
}