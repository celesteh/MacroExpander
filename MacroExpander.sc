MacroExpander : IdentityDictionary{

	var doc;//, macros;

	*new {|document, title|
		^super.new.init(document, title);
	}

	init{|document, title|

		var switch;
		//macros = IdentityDictionary.new;
		//doc.class.postln;

		// This is for live coding. Accidents happen
		if(document.isKindOf(String) && (title.isNil || title.isKindOf(Document)), {
			switch = title;
			title = document;
			doc = title;
		}, {
			doc = document;
		});

		title.isNil.if({
			title = "Live Code";
		});

		doc.class.postln;

		doc.isNil.if({
			doc = Document(title);
		});


		doc. keyDownAction = {|doc, char, mod, unicode, keycode|
			var code, altDown, offset, start, end, key, value, size;//, string;

			//[mod, keycode, unicode].postln;

			altDown = Platform.case(
				\osx, {((keycode==126) || (keycode==40))}, //this is probably wrong
				\linux,{(keycode==65364)},
				\windows, // I don't know, so here's a copy of osx
				{((keycode==126) || (keycode==40))}
			);

			if(mod.isAlt && altDown.value, {
				//"alt down".postln;
				code = doc.selectedString;
				if( code.isNil || code =="", {
					//"no selected string".postln;
					code = doc.currentLine;
				});

				//doc.string.at(doc.selectionStart).postln;
				start = doc.string.findBackwards("\n", false,
					doc.selectionStart-1);
				start.isNil.if({ start = -1 });//, {start = start+1});
				end = doc.string.find("\n", false,
					start+1);
				end.isNil.if({ end = start });//, {end = end-1});
				//doc.selectRange(start, end);
				if( code.isNil || code =="", {
					code = doc.string.copyRange(start+1, end);
				});
				// Did we get any code?
				if ( code.notNil, {
					code = code.stripWhiteSpace;

					if (code != "" &&
						"[a-zA-Z]".matchRegexp(code), {

							//"code is %".format(code).postln;

							//doc.selectedString;
							//doc.string.postln;
							doc.selectRange(start+1, end-start);

							// if you put the symbol \owl in the Dictionary
							// and then type \owl in the Document,
							// it doesn't match because the \ isn't treated
							// as a symbol marker but
							// as a character within the key

							size = code.size;

							code.beginsWith("\\").if({
								code.removeAt(0);
							});

							key = code.asSymbol;
							value = this.at(key);

							value.notNil.if({

								doc.selectedString = value.asString;
								doc.selectRange(start+1, value.size);
							});
					})
				});
			})
		}


	}
}
