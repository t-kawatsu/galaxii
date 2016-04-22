package com.galaxii.common.util;

import java.io.ByteArrayOutputStream;

/**
 * @see http://blog.app-works.org/archives/194
 * @see http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=4
 */
public class ExecCommand {
	private byte[] stdout;
	private byte[] stderr;
	private Integer exitCode;

	public ExecCommand(String command) {
		try {
			ByteArrayOutputStream out;
			ByteArrayOutputStream err;
			StreamGobbler outGobbler;
			StreamGobbler errGobbler;

			// コマンド実行プロセス開始
			Process proc = Runtime.getRuntime().exec(command);

			// 標準出力Streamの読み込みスレッド開始
			out = new ByteArrayOutputStream();
			outGobbler = new StreamGobbler(proc.getInputStream(), out);
			outGobbler.start();

			// エラー出力Streamの読み込みスレッド開始
			err = new ByteArrayOutputStream();
			errGobbler = new StreamGobbler(proc.getErrorStream(), err);
			errGobbler.start();

			// すべての処理が終わるまで待機
			this.exitCode = proc.waitFor();
			outGobbler.join();
			errGobbler.join();
			this.stdout = out.toByteArray();
			this.stderr = err.toByteArray();

			out.close();
			err.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	public byte[] getStdout() {
		return stdout;
	}
	public byte[] getStderr() {
		return stderr;
	}
	public Integer getExitCode() {
		return exitCode;
	}
}
