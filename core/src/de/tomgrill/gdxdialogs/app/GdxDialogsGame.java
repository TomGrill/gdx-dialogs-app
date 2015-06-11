package de.tomgrill.gdxdialogs.app;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import de.tomgrill.gdxdialogs.app.actors.BitmapFontActor;
import de.tomgrill.gdxdialogs.app.actors.ButtonActor;
import de.tomgrill.gdxdialogs.core.DialogManager;
import de.tomgrill.gdxdialogs.core.DialogSystem;
import de.tomgrill.gdxdialogs.core.dialogs.ButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.ProgressDialog;
import de.tomgrill.gdxdialogs.core.dialogs.TextPrompt;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class GdxDialogsGame extends ApplicationAdapter {
	SpriteBatch batch;

	private Stage stage;

	private ButtonActor singleButtonActor;
	private ButtonActor doubleButtonActor;
	private ButtonActor trippleButtonActor;
	private ButtonActor progressButtonActor;
	private ButtonActor textPromptButtonActor;

	private DialogManager dManager;

	private BitmapFontActor buttonClickedFontActor;
	private BitmapFontActor textPromptFontActor;

	private long showProgressUntilTime;

	private ProgressDialog progressDialog;

	@Override
	public void create() {
		batch = new SpriteBatch();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		stage = new Stage(new ExtendViewport(640, 800, 640, 800));
		Gdx.input.setInputProcessor(stage);

		DialogSystem dSystem = new DialogSystem();
		dManager = dSystem.getDialogManager();

		BitmapFont font = new BitmapFont();
		font.setColor(Color.BLACK);
		// font.getData().setScale(2f);
		buttonClickedFontActor = new BitmapFontActor(font);

		buttonClickedFontActor.setX(30);
		buttonClickedFontActor.setY(500);
		buttonClickedFontActor.setText("Last clicked button: none");

		stage.addActor(buttonClickedFontActor);

		textPromptFontActor = new BitmapFontActor(font);
		textPromptFontActor.setX(30);
		textPromptFontActor.setY(150);
		textPromptFontActor.setText("Your name: unknown");

		stage.addActor(textPromptFontActor);

		createSingleButtonDialog();
		createDoubleButtonDialog();
		createTrippleButtonDialog();

		createProgressDialog();

		createTextPromptDialog();
	}

	private void createTextPromptDialog() {
		textPromptButtonActor = new ButtonActor(new TextureRegion(new Texture("textprompt.png")));

		textPromptButtonActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				showANewTextPromptsDialog();
			}

		});

		textPromptButtonActor.setX(640 / 2f - 219 / 2f);
		textPromptButtonActor.setY(200);
		textPromptButtonActor.setHeight(40);
		textPromptButtonActor.setWidth(219);

		textPromptButtonActor.setVisible(true);

		stage.addActor(textPromptButtonActor);

	}

	private void showANewTextPromptsDialog() {
		TextPrompt textPrompt = dManager.newTextPrompt();

		textPrompt.setTitle("Your name");
		textPrompt.setMessage("Please tell me your name.");

		textPrompt.setCancelButtonLabel("Cancel");
		textPrompt.setConfirmButtonLabel("Save name");

		textPrompt.setTextPromptListener(new TextPromptListener() {

			@Override
			public void confirm(String text) {
				textPromptFontActor.setText("Your name is: " + text);
			}

			@Override
			public void cancel() {
				textPromptFontActor.setText("Common, tell me your name please :)");
			}
		});

		textPrompt.build().show();

	}

	private void createProgressDialog() {
		progressButtonActor = new ButtonActor(new TextureRegion(new Texture("progress.png")));

		progressButtonActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				showANewProgressDialog();
			}

		});

		progressButtonActor.setX(640 / 2f - 387 / 2f);
		progressButtonActor.setY(400);
		progressButtonActor.setHeight(40);
		progressButtonActor.setWidth(387);

		progressButtonActor.setVisible(true);

		stage.addActor(progressButtonActor);

	}

	private void showANewProgressDialog() {
		progressDialog = dManager.newProgressDialog();

		progressDialog.setTitle("Download");
		progressDialog.setMessage("Loading new level from server...");

		showProgressUntilTime = TimeUtils.millis() + 5000;
		progressDialog.build().show();

	}

	private void createTrippleButtonDialog() {
		trippleButtonActor = new ButtonActor(new TextureRegion(new Texture("3button.png")));

		trippleButtonActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				showANewTrippleButtonDialog();
			}

		});

		trippleButtonActor.setX(640 / 2f - 310 / 2f);
		trippleButtonActor.setY(560);
		trippleButtonActor.setHeight(40);
		trippleButtonActor.setWidth(310);

		trippleButtonActor.setVisible(true);

		stage.addActor(trippleButtonActor);

	}

	private void showANewTrippleButtonDialog() {
		ButtonDialog bDialog = dManager.newButtonDialog();

		bDialog.setTitle("Buy a item");
		bDialog.setMessage("Do you want to buy the mozarella?");

		bDialog.setClickListener(new ButtonClickListener() {

			@Override
			public void click(int button) {
				buttonClickedFontActor.setText("Last clicked button: " + button);

			}
		});

		bDialog.addButton("Yes, nomnom!");
		bDialog.addButton("No");
		bDialog.addButton("Later");

		bDialog.build().show();

	}

	private void createDoubleButtonDialog() {
		doubleButtonActor = new ButtonActor(new TextureRegion(new Texture("2button.png")));

		doubleButtonActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				showANewDoubleButtonDialog();
			}

		});

		doubleButtonActor.setX(640 / 2f - 310 / 2f);
		doubleButtonActor.setY(630);
		doubleButtonActor.setHeight(40);
		doubleButtonActor.setWidth(310);

		doubleButtonActor.setVisible(true);

		stage.addActor(doubleButtonActor);

	}

	private void showANewDoubleButtonDialog() {
		ButtonDialog bDialog = dManager.newButtonDialog();

		bDialog.setTitle("Am i right?");
		bDialog.setMessage("Your age is 29!");

		bDialog.setClickListener(new ButtonClickListener() {

			@Override
			public void click(int button) {
				buttonClickedFontActor.setText("Last clicked button: " + button);

			}
		});

		bDialog.addButton("Correct!");
		bDialog.addButton("Wrong");

		bDialog.build().show();

	}

	private void createSingleButtonDialog() {
		singleButtonActor = new ButtonActor(new TextureRegion(new Texture("1button.png")));

		singleButtonActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				showANewSingleButtonDialog();
			}

		});

		singleButtonActor.setX(640 / 2f - 300 / 2f);
		singleButtonActor.setY(700);
		singleButtonActor.setHeight(40);
		singleButtonActor.setWidth(300);

		singleButtonActor.setVisible(true);

		stage.addActor(singleButtonActor);
	}

	private void showANewSingleButtonDialog() {
		ButtonDialog bDialog = dManager.newButtonDialog();

		bDialog.setTitle("Do you like me?");
		bDialog.setMessage("I'am a single button. Do you like that?");

		bDialog.setClickListener(new ButtonClickListener() {

			@Override
			public void click(int button) {
				buttonClickedFontActor.setText("Last clicked button: " + button);

			}
		});

		bDialog.addButton("I looooooooove it!");

		bDialog.build().show();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		if (showProgressUntilTime < TimeUtils.millis()) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
